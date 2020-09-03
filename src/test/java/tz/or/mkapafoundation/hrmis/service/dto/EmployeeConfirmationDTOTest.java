package tz.or.mkapafoundation.hrmis.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class EmployeeConfirmationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeConfirmationDTO.class);
        EmployeeConfirmationDTO employeeConfirmationDTO1 = new EmployeeConfirmationDTO();
        employeeConfirmationDTO1.setId(1L);
        EmployeeConfirmationDTO employeeConfirmationDTO2 = new EmployeeConfirmationDTO();
        assertThat(employeeConfirmationDTO1).isNotEqualTo(employeeConfirmationDTO2);
        employeeConfirmationDTO2.setId(employeeConfirmationDTO1.getId());
        assertThat(employeeConfirmationDTO1).isEqualTo(employeeConfirmationDTO2);
        employeeConfirmationDTO2.setId(2L);
        assertThat(employeeConfirmationDTO1).isNotEqualTo(employeeConfirmationDTO2);
        employeeConfirmationDTO1.setId(null);
        assertThat(employeeConfirmationDTO1).isNotEqualTo(employeeConfirmationDTO2);
    }
}
