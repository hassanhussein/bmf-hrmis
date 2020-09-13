package tz.or.mkapafoundation.hrmis.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class GeographicZoneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicZoneDTO.class);
        GeographicZoneDTO geographicZoneDTO1 = new GeographicZoneDTO();
        geographicZoneDTO1.setId(1L);
        GeographicZoneDTO geographicZoneDTO2 = new GeographicZoneDTO();
        assertThat(geographicZoneDTO1).isNotEqualTo(geographicZoneDTO2);
        geographicZoneDTO2.setId(geographicZoneDTO1.getId());
        assertThat(geographicZoneDTO1).isEqualTo(geographicZoneDTO2);
        geographicZoneDTO2.setId(2L);
        assertThat(geographicZoneDTO1).isNotEqualTo(geographicZoneDTO2);
        geographicZoneDTO1.setId(null);
        assertThat(geographicZoneDTO1).isNotEqualTo(geographicZoneDTO2);
    }
}
