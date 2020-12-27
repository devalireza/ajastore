package com.aja.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aja.store.web.rest.TestUtil;

public class ItemInstanceTransactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemInstanceTransact.class);
        ItemInstanceTransact itemInstanceTransact1 = new ItemInstanceTransact();
        itemInstanceTransact1.setId(1L);
        ItemInstanceTransact itemInstanceTransact2 = new ItemInstanceTransact();
        itemInstanceTransact2.setId(itemInstanceTransact1.getId());
        assertThat(itemInstanceTransact1).isEqualTo(itemInstanceTransact2);
        itemInstanceTransact2.setId(2L);
        assertThat(itemInstanceTransact1).isNotEqualTo(itemInstanceTransact2);
        itemInstanceTransact1.setId(null);
        assertThat(itemInstanceTransact1).isNotEqualTo(itemInstanceTransact2);
    }
}
