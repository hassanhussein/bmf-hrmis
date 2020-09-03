package tz.or.mkapafoundation.hrmis.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarderMapperTest {

    private CarderMapper carderMapper;

    @BeforeEach
    public void setUp() {
        carderMapper = new CarderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(carderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(carderMapper.fromId(null)).isNull();
    }
}
