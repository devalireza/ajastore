package com.aja.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aja.store.web.rest.TestUtil;

public class ItemInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemInstance.class);
        ItemInstance itemInstance1 = new ItemInstance();
        itemInstance1.setId(1L);
        ItemInstance itemInstance2 = new ItemInstance();
        itemInstance2.setId(itemInstance1.getId());
        assertThat(itemInstance1).isEqualTo(itemInstance2);
        itemInstance2.setId(2L);
        assertThat(itemInstance1).isNotEqualTo(itemInstance2);
        itemInstance1.setId(null);
        assertThat(itemInstance1).isNotEqualTo(itemInstance2);
    }
}
