package tz.or.mkapafoundation.hrmis.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class FacilityTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityTypeDTO.class);
        FacilityTypeDTO facilityTypeDTO1 = new FacilityTypeDTO();
        facilityTypeDTO1.setId(1L);
        FacilityTypeDTO facilityTypeDTO2 = new FacilityTypeDTO();
        assertThat(facilityTypeDTO1).isNotEqualTo(facilityTypeDTO2);
        facilityTypeDTO2.setId(facilityTypeDTO1.getId());
        assertThat(facilityTypeDTO1).isEqualTo(facilityTypeDTO2);
        facilityTypeDTO2.setId(2L);
        assertThat(facilityTypeDTO1).isNotEqualTo(facilityTypeDTO2);
        facilityTypeDTO1.setId(null);
        assertThat(facilityTypeDTO1).isNotEqualTo(facilityTypeDTO2);
    }
}
