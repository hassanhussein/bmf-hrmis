package tz.or.mkapafoundation.hrmis.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class GeographicZoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicZone.class);
        GeographicZone geographicZone1 = new GeographicZone();
        geographicZone1.setId(1L);
        GeographicZone geographicZone2 = new GeographicZone();
        geographicZone2.setId(geographicZone1.getId());
        assertThat(geographicZone1).isEqualTo(geographicZone2);
        geographicZone2.setId(2L);
        assertThat(geographicZone1).isNotEqualTo(geographicZone2);
        geographicZone1.setId(null);
        assertThat(geographicZone1).isNotEqualTo(geographicZone2);
    }
}
