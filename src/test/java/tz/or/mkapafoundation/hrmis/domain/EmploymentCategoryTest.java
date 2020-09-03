package tz.or.mkapafoundation.hrmis.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class EmploymentCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmploymentCategory.class);
        EmploymentCategory employmentCategory1 = new EmploymentCategory();
        employmentCategory1.setId(1L);
        EmploymentCategory employmentCategory2 = new EmploymentCategory();
        employmentCategory2.setId(employmentCategory1.getId());
        assertThat(employmentCategory1).isEqualTo(employmentCategory2);
        employmentCategory2.setId(2L);
        assertThat(employmentCategory1).isNotEqualTo(employmentCategory2);
        employmentCategory1.setId(null);
        assertThat(employmentCategory1).isNotEqualTo(employmentCategory2);
    }
}
