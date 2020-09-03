package tz.or.mkapafoundation.hrmis.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class EmployeeConfirmationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeConfirmation.class);
        EmployeeConfirmation employeeConfirmation1 = new EmployeeConfirmation();
        employeeConfirmation1.setId(1L);
        EmployeeConfirmation employeeConfirmation2 = new EmployeeConfirmation();
        employeeConfirmation2.setId(employeeConfirmation1.getId());
        assertThat(employeeConfirmation1).isEqualTo(employeeConfirmation2);
        employeeConfirmation2.setId(2L);
        assertThat(employeeConfirmation1).isNotEqualTo(employeeConfirmation2);
        employeeConfirmation1.setId(null);
        assertThat(employeeConfirmation1).isNotEqualTo(employeeConfirmation2);
    }
}
