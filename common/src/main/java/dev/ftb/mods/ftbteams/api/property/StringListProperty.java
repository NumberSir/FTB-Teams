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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class StringListProperty extends TeamProperty<List<String>> {
    public StringListProperty(Identifier id, Supplier<List<String>> def) {
        super(id, def);
    }

    public StringListProperty(Identifier id, List<String> def) {
        this(id, () -> def);
    }

    static StringListProperty fromNetwork(Identifier id, FriendlyByteBuf buf) {
        return new StringListProperty(id, buf.readList(b -> b.readUtf(Short.MAX_VALUE)));
    }

    @Override
    public TeamPropertyType<List<String>> getType() {
        return TeamPropertyType.STRING_LIST;
    }

    @Override
    public Optional<List<String>> fromString(String string) {
        return string.length() > 2 && string.startsWith("[") && string.endsWith("]") ?
                // the "new ArrayList(...)" part is important here!
                Optional.of(new ArrayList<>(Arrays.asList(string.substring(1, string.length() - 1).split("\t")))) :
                Optional.empty();
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeCollection(getDefaultValue(), FriendlyByteBuf::writeUtf);
    }

    @Override
    public String toString(List<String> value) {
        return "[" + String.join("\t", value) + "]";
    }

    @Override
    public EditableConfigValue<?> config(EditableConfigGroup config, TeamPropertyValue<List<String>> value) {
        return config.addList(id.getPath(), value.getValue(), new EditableString(), "");
    }

    @Override
    public Json5Element toJson(List<String> value) {
        return value.stream().map(Json5Primitive::fromString).collect(Json5Array::new, Json5Array::add, Json5Array::addAll);
    }

    @Override
    public Optional<List<String>> fromJson(Json5Element json) {
        if (json instanceof Json5Array a) {
            return Optional.of(a.asList().stream().map(Json5Element::getAsString).toList());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void writeValue(RegistryFriendlyByteBuf buf, List<String> value) {
        buf.writeCollection(value, FriendlyByteBuf::writeUtf);
    }

    @Override
    public List<String> readValue(RegistryFriendlyByteBuf buf) {
        return buf.readList(FriendlyByteBuf::readUtf);
    }
}
