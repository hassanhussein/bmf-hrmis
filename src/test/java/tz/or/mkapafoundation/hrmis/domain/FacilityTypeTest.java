package tz.or.mkapafoundation.hrmis.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class FacilityTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacilityType.class);
        FacilityType facilityType1 = new FacilityType();
        facilityType1.setId(1L);
        FacilityType facilityType2 = new FacilityType();
        facilityType2.setId(facilityType1.getId());
        assertThat(facilityType1).isEqualTo(facilityType2);
        facilityType2.setId(2L);
        assertThat(facilityType1).isNotEqualTo(facilityType2);
        facilityType1.setId(null);
        assertThat(facilityType1).isNotEqualTo(facilityType2);
    }
}
