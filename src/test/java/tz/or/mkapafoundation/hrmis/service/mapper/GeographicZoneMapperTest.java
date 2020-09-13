package tz.or.mkapafoundation.hrmis.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GeographicZoneMapperTest {

    private GeographicZoneMapper geographicZoneMapper;

    @BeforeEach
    public void setUp() {
        geographicZoneMapper = new GeographicZoneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(geographicZoneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(geographicZoneMapper.fromId(null)).isNull();
    }
}
