package tz.or.mkapafoundation.hrmis.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class EmployeeRecordDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRecordDTO.class);
        EmployeeRecordDTO employeeRecordDTO1 = new EmployeeRecordDTO();
        employeeRecordDTO1.setId(1L);
        EmployeeRecordDTO employeeRecordDTO2 = new EmployeeRecordDTO();
        assertThat(employeeRecordDTO1).isNotEqualTo(employeeRecordDTO2);
        employeeRecordDTO2.setId(employeeRecordDTO1.getId());
        assertThat(employeeRecordDTO1).isEqualTo(employeeRecordDTO2);
        employeeRecordDTO2.setId(2L);
        assertThat(employeeRecordDTO1).isNotEqualTo(employeeRecordDTO2);
        employeeRecordDTO1.setId(null);
        assertThat(employeeRecordDTO1).isNotEqualTo(employeeRecordDTO2);
    }
}
