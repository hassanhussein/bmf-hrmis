package tz.or.mkapafoundation.hrmis.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FacilityTypeMapperTest {

    private FacilityTypeMapper facilityTypeMapper;

    @BeforeEach
    public void setUp() {
        facilityTypeMapper = new FacilityTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(facilityTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(facilityTypeMapper.fromId(null)).isNull();
    }
}
