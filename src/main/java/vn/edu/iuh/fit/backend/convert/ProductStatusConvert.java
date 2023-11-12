package vn.edu.iuh.fit.backend.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import vn.edu.iuh.fit.backend.enums.ProductStatus;

@Converter(autoApply = true)
public class ProductStatusConvert implements AttributeConverter<ProductStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProductStatus attribute) {
        if(attribute==null){
            return -1;
        }
        return attribute.getValue();
    }

    @Override
    public ProductStatus convertToEntityAttribute(Integer dbData) {
        return  dbData==1?ProductStatus.ACTIVE:dbData==0?ProductStatus.IN_ACTIVE:ProductStatus.TERMINATED;
    }
}
