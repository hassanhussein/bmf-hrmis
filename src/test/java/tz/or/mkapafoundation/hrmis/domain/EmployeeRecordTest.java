package tz.or.mkapafoundation.hrmis.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class EmployeeRecordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRecord.class);
        EmployeeRecord employeeRecord1 = new EmployeeRecord();
        employeeRecord1.setId(1L);
        EmployeeRecord employeeRecord2 = new EmployeeRecord();
        employeeRecord2.setId(employeeRecord1.getId());
        assertThat(employeeRecord1).isEqualTo(employeeRecord2);
        employeeRecord2.setId(2L);
        assertThat(employeeRecord1).isNotEqualTo(employeeRecord2);
        employeeRecord1.setId(null);
        assertThat(employeeRecord1).isNotEqualTo(employeeRecord2);
    }
}
