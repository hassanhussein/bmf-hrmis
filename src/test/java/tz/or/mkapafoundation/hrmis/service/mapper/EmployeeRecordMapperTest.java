package tz.or.mkapafoundation.hrmis.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeRecordMapperTest {

    private EmployeeRecordMapper employeeRecordMapper;

    @BeforeEach
    public void setUp() {
        employeeRecordMapper = new EmployeeRecordMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeRecordMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeRecordMapper.fromId(null)).isNull();
    }
}
