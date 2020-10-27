package Converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import egreso.TipoMedioPago;

import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class TipoMedioDePagoConverter implements AttributeConverter<TipoMedioPago, String> {


	@Override
	public String convertToDatabaseColumn(TipoMedioPago attribute) {
		return attribute == null ? null : attribute.toString();
	}

	@Override
	public TipoMedioPago convertToEntityAttribute(String dbData) {
		return dbData == null ? null : TipoMedioPago.valueOf(dbData);
	}
}