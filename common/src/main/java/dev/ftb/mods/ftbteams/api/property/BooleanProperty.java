package dev.ftb.mods.ftbteams.api.property;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.ftb.mods.ftblibrary.client.config.EditableConfigGroup;
import dev.ftb.mods.ftblibrary.client.config.editable.EditableConfigValue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;

import java.util.Optional;
import java.util.function.Supplier;

public class BooleanProperty extends TeamProperty<Boolean> {
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static final Optional<Boolean> TRUE = Optional.of(Boolean.TRUE);
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static final Optional<Boolean> FALSE = Optional.of(Boolean.FALSE);

	public BooleanProperty(Identifier id, Supplier<Boolean> def) {
		super(id, def);
	}

	public BooleanProperty(Identifier id, Boolean def) {
		this(id, () -> def);
	}

	static BooleanProperty fromNetwork(Identifier id, FriendlyByteBuf buf) {
		return new BooleanProperty(id, buf.readBoolean());
	}

	@Override
	public TeamPropertyType<Boolean> getType() {
		return TeamPropertyType.BOOLEAN;
	}

	@Override
	public Optional<Boolean> fromString(String string) {
		if (string.equals("true")) {
			return TRUE;
		} else if (string.equals("false")) {
			return FALSE;
		}

		return Optional.empty();
	}

	@Override
	public void write(RegistryFriendlyByteBuf buf) {
		buf.writeBoolean(getDefaultValue());
	}

	@Override
	public EditableConfigValue<?> config(EditableConfigGroup config, TeamPropertyValue<Boolean> value) {
		return config.addBool(id.getPath(), value.getValue(), value::setValue, getDefaultValue());
	}

	@Override
	public Json5Element toJson(Boolean value) {
		return Json5Primitive.fromBoolean(value);
	}

	@Override
	public Optional<Boolean> fromJson(Json5Element json) {
        return json instanceof Json5Primitive p && p.isNumber() && p.getAsByte() == 1 ? TRUE : FALSE;
    }

	@Override
	public void writeValue(RegistryFriendlyByteBuf buf, Boolean value) {
		buf.writeBoolean(value);
	}

	@Override
	public Boolean readValue(RegistryFriendlyByteBuf buf) {
		return buf.readBoolean();
	}
}
