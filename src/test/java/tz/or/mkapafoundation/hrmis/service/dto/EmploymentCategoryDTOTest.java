package tz.or.mkapafoundation.hrmis.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class EmploymentCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmploymentCategoryDTO.class);
        EmploymentCategoryDTO employmentCategoryDTO1 = new EmploymentCategoryDTO();
        employmentCategoryDTO1.setId(1L);
        EmploymentCategoryDTO employmentCategoryDTO2 = new EmploymentCategoryDTO();
        assertThat(employmentCategoryDTO1).isNotEqualTo(employmentCategoryDTO2);
        employmentCategoryDTO2.setId(employmentCategoryDTO1.getId());
        assertThat(employmentCategoryDTO1).isEqualTo(employmentCategoryDTO2);
        employmentCategoryDTO2.setId(2L);
        assertThat(employmentCategoryDTO1).isNotEqualTo(employmentCategoryDTO2);
        employmentCategoryDTO1.setId(null);
        assertThat(employmentCategoryDTO1).isNotEqualTo(employmentCategoryDTO2);
    }
}
