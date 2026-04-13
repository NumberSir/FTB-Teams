package dev.ftb.mods.ftbteams.api.property;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.ftb.mods.ftblibrary.client.config.EditableConfigGroup;
import dev.ftb.mods.ftblibrary.client.config.editable.EditableConfigValue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Optional;
import java.util.function.Supplier;

public class DoubleProperty extends TeamProperty<Double> {
	public final double minValue;
	public final double maxValue;

	public DoubleProperty(Identifier id, Supplier<Double> def, double min, double max) {
		super(id, def);
		minValue = min;
		maxValue = max;
	}

	public DoubleProperty(Identifier id, double def, double min, double max) {
		this(id, () -> def, min, max);
	}

	public DoubleProperty(Identifier id, Supplier<Double> def) {
		this(id, def, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	static DoubleProperty fromNetwork(Identifier id, FriendlyByteBuf buf) {
		return new DoubleProperty(id, buf.readDouble(), buf.readDouble(), buf.readDouble());
	}

	@Override
	public TeamPropertyType<Double> getType() {
		return TeamPropertyType.DOUBLE;
	}

	@Override
	public Optional<Double> fromString(String string) {
		try {
			double num = Double.parseDouble(string);
			return Optional.of(Mth.clamp(num, minValue, maxValue));
		} catch (Exception ex) {
			return Optional.empty();
		}
	}

	@Override
	public void write(RegistryFriendlyByteBuf buf) {
		buf.writeDouble(getDefaultValue());
		buf.writeDouble(minValue);
		buf.writeDouble(maxValue);
	}

	@Override
	public EditableConfigValue<?> config(EditableConfigGroup config, TeamPropertyValue<Double> value) {
		return config.addDouble(id.getPath(), value.getValue(), value::setValue, getDefaultValue(), minValue, maxValue);
	}

	@Override
	public Json5Element toJson(Double value) {
		return Json5Primitive.fromNumber(value);
	}

	@Override
	public Optional<Double> fromJson(@UnknownNullability Json5Element json) {
        return json instanceof Json5Primitive p && p.isNumber() ?
				Optional.of(Mth.clamp(p.getAsDouble(), minValue, maxValue)) :
				Optional.empty();
    }

	@Override
	public Double readValue(RegistryFriendlyByteBuf buf) {
		return buf.readDouble();
	}

	@Override
	public void writeValue(RegistryFriendlyByteBuf buf, Double value) {
		buf.writeDouble(value);
	}
}
