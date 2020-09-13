package tz.or.mkapafoundation.hrmis.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GeographicLevelMapperTest {

    private GeographicLevelMapper geographicLevelMapper;

    @BeforeEach
    public void setUp() {
        geographicLevelMapper = new GeographicLevelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(geographicLevelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(geographicLevelMapper.fromId(null)).isNull();
    }
}
