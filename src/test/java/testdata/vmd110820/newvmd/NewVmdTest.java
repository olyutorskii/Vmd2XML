/*
 */

package testdata.vmd110820.newvmd;

import org.junit.jupiter.api.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class NewVmdTest {

    static Class<?> THISCLASS = NewVmdTest.class;

    public NewVmdTest() {
        assert this.getClass() == THISCLASS;
        return;
    }

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2OldXml(THISCLASS, "newvmd.vmd", "newvmd.xml");
        return;
    }

}
