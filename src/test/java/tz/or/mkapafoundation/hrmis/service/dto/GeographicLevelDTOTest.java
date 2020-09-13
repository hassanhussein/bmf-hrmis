package tz.or.mkapafoundation.hrmis.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class GeographicLevelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicLevelDTO.class);
        GeographicLevelDTO geographicLevelDTO1 = new GeographicLevelDTO();
        geographicLevelDTO1.setId(1L);
        GeographicLevelDTO geographicLevelDTO2 = new GeographicLevelDTO();
        assertThat(geographicLevelDTO1).isNotEqualTo(geographicLevelDTO2);
        geographicLevelDTO2.setId(geographicLevelDTO1.getId());
        assertThat(geographicLevelDTO1).isEqualTo(geographicLevelDTO2);
        geographicLevelDTO2.setId(2L);
        assertThat(geographicLevelDTO1).isNotEqualTo(geographicLevelDTO2);
        geographicLevelDTO1.setId(null);
        assertThat(geographicLevelDTO1).isNotEqualTo(geographicLevelDTO2);
    }
}
