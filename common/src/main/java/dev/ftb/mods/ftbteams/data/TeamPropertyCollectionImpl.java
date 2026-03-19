package dev.ftb.mods.ftbteams.data;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import dev.ftb.mods.ftblibrary.platform.event.NativeEventPosting;
import dev.ftb.mods.ftbteams.api.event.CollectTeamPropertiesEvent;
import dev.ftb.mods.ftbteams.api.property.TeamProperty;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyType;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyValue;
import net.minecraft.IdentifierException;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class TeamPropertyCollectionImpl implements TeamPropertyCollection {
	private final PropertyMap map = new PropertyMap();

	public static final StreamCodec<RegistryFriendlyByteBuf, TeamPropertyCollection> STREAM_CODEC = StreamCodec.of(
			(buffer, props) -> {
				buffer.writeVarInt(props.size());
				props.forEach((prop, value) -> {
					TeamPropertyType.write(buffer, prop);
					prop.writeValue(buffer, value.getValue());
				});
			},
			buffer -> {
				TeamPropertyCollectionImpl props = new TeamPropertyCollectionImpl();
				int nProperties = buffer.readVarInt();
				for (int i = 0; i < nProperties; i++) {
					TeamProperty<?> tp = TeamPropertyType.read(buffer);
					props.map.putPropertyFromNetwork(tp, buffer);
				}
				return props;
			}
	);

	public void collectProperties() {
		map.clear();

		NativeEventPosting.INSTANCE.postEvent(new CollectTeamPropertiesEvent.Data(map::putDefaultProperty));
//		TeamEvent.COLLECT_PROPERTIES.invoker().accept(new TeamCollectPropertiesEvent(map::putDefaultProperty));
	}

	@Override
	public <T> void forEach(BiConsumer<TeamProperty<T>, TeamPropertyValue<T>> consumer) {
		map.forEachProperty(consumer);
	}

	@Override
	public TeamPropertyCollectionImpl copy() {
		return copyIf(p -> true);
	}

	@Override
	public void updateFrom(TeamPropertyCollection otherProperties) {
		otherProperties.forEach((key, value) -> set(key, value.getValue()));
	}

	@Override
	public <T> T get(TeamProperty<T> key) {
		TeamPropertyValue<T> v = map.getProperty(key);
		return v == null ? key.getDefaultValue() : v.getValue();
	}

	@Override
	public <T> void set(TeamProperty<T> key, T value) {
		if (map.hasProperty(key)) {
			Objects.requireNonNull(map.getProperty(key)).setValue(value);
		} else {
			map.putProperty(key, new TeamPropertyValue<>(key, value));
		}
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public TeamPropertyCollectionImpl copyIf(Predicate<TeamProperty<?>> predicate) {
		TeamPropertyCollectionImpl p = new TeamPropertyCollectionImpl();

		map.forEachProperty((key, value) -> {
			if (predicate.test(key)) {
				p.map.putProperty(key, value.copy());
			}
		});

		return p;
	}

	public void toJson(RegistryFriendlyByteBuf buffer) {
		TeamPropertyCollectionImpl.STREAM_CODEC.encode(buffer, this);
	}

	public void writeSyncableOnly(RegistryFriendlyByteBuf buffer) {
		// this is used when sync'ing team data for a different team
		// player A only needs to know limited info (display name, color...) about team B if A isn't a member of B
		PropertyMap subMap = new PropertyMap();
		map.forEachProperty((prop, val) -> {
			if (prop.shouldSyncToAll()) {
				subMap.backingMap.put(prop, map.backingMap.get(prop));
			}
		});

		buffer.writeVarInt(subMap.size());
		subMap.forEachProperty((key, value) -> {
			TeamPropertyType.write(buffer, key);
			key.writeValue(buffer, value.getValue());
		});
	}

	public void read(Json5Object json) {
		json.asMap().forEach((key, val) ->
				map.findProperty(key).ifPresent(prop -> map.putPropertyFromJson(prop, val)));
	}

	public Json5Object toJson(Json5Object tag) {
		map.forEachProperty((key, value) ->
				tag.add(key.getId().toString(), key.toJson(value.getValue())));

		return tag;
	}

	private static class PropertyMap {
		final Map<Object, Object> backingMap = new LinkedHashMap<>();
		final Map<Identifier, TeamProperty<?>> byId = new HashMap<>();

		void clear() {
			backingMap.clear();
			byId.clear();
		}

		boolean hasProperty(TeamProperty<?> prop) {
			return backingMap.containsKey(prop);
		}

		int size() {
			return backingMap.size();
		}

		<T> void putProperty(TeamProperty<T> prop, TeamPropertyValue<T> value) {
			backingMap.put(prop, value);
			byId.put(prop.getId(), prop);
		}

		void putDefaultProperty(TeamProperty<?> prop) {
			backingMap.put(prop, TeamPropertyValue.createDefaultValue(prop));
			byId.put(prop.getId(), prop);
		}

		void putPropertyFromNetwork(TeamProperty<?> prop, RegistryFriendlyByteBuf buffer) {
			backingMap.put(prop, TeamPropertyValue.fromNetwork(prop, buffer));
			byId.put(prop.getId(), prop);
		}

		void putPropertyFromJson(TeamProperty<?> prop, Json5Element tag) {
			backingMap.put(prop, TeamPropertyValue.fromJson(prop, tag));
			byId.put(prop.getId(), prop);
		}

		@Nullable
		<T> TeamPropertyValue<T> getProperty(TeamProperty<T> property) {
			//noinspection unchecked
			return (TeamPropertyValue<T>) backingMap.get(property);
		}

		<T> void forEachProperty(BiConsumer<TeamProperty<T>, TeamPropertyValue<T>> consumer) {
			//noinspection unchecked
			backingMap.forEach((k, v) -> consumer.accept((TeamProperty<T>) k, (TeamPropertyValue<T>) v));
		}

		Optional<TeamProperty<?>> findProperty(String key) {
			try {
				return Optional.ofNullable(byId.get(Identifier.tryParse(key)));
			} catch (IdentifierException e) {
				return Optional.empty();
			}
		}
	}
}
