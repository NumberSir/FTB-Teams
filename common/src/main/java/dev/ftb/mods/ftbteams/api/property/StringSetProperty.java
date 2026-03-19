package dev.ftb.mods.ftbteams.api.property;

import de.marhali.json5.Json5Array;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.ftb.mods.ftblibrary.client.config.EditableConfigGroup;
import dev.ftb.mods.ftblibrary.client.config.editable.EditableConfigValue;
import dev.ftb.mods.ftblibrary.client.config.editable.EditableString;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StringSetProperty extends TeamProperty<Set<String>> {
    public StringSetProperty(Identifier id, Supplier<Set<String>> def) {
        super(id, def);
    }

    public StringSetProperty(Identifier id, Set<String> def) {
        this(id, () -> def);
    }

    static StringSetProperty fromNetwork(Identifier id, FriendlyByteBuf buf) {
        return new StringSetProperty(id, new HashSet<>(buf.readList(b -> b.readUtf(Short.MAX_VALUE))));
    }

    @Override
    public TeamPropertyType<Set<String>> getType() {
        return TeamPropertyType.STRING_SET;
    }

    @Override
    public Optional<Set<String>> fromString(String string) {
        return string.length() > 2 && string.startsWith("[") && string.endsWith("]") ?
                Optional.of(new HashSet<>(Arrays.asList(string.substring(1, string.length() - 1).split("\t")))) :
                Optional.empty();
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeCollection(getDefaultValue(), FriendlyByteBuf::writeUtf);
    }

    @Override
    public String toString(Set<String> value) {
        return "[" + String.join("\t", value) + "]";
    }

    @Override
    public EditableConfigValue<?> config(EditableConfigGroup config, TeamPropertyValue<Set<String>> value) {
        return config.addList(id.getPath(), new ArrayList<>(value.getValue()), new EditableString(), "");
    }

    @Override
    public Json5Element toJson(Set<String> value) {
        return value.stream().map(Json5Primitive::fromString).collect(Json5Array::new, Json5Array::add, Json5Array::addAll);
    }

    @Override
    public Optional<Set<String>> fromJson(Json5Element json) {
        if (json instanceof Json5Array a) {
            return Optional.of(a.asList().stream().map(Json5Element::getAsString).collect(Collectors.toSet()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void writeValue(RegistryFriendlyByteBuf buf, Set<String> value) {
        buf.writeCollection(value, FriendlyByteBuf::writeUtf);
    }

    @Override
    public Set<String> readValue(RegistryFriendlyByteBuf buf) {
        return buf.readCollection(HashSet::new, FriendlyByteBuf::readUtf);
    }
}
