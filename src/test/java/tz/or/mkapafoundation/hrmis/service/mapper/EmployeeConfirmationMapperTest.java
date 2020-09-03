package tz.or.mkapafoundation.hrmis.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeConfirmationMapperTest {

    private EmployeeConfirmationMapper employeeConfirmationMapper;

    @BeforeEach
    public void setUp() {
        employeeConfirmationMapper = new EmployeeConfirmationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeConfirmationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeConfirmationMapper.fromId(null)).isNull();
    }
}
