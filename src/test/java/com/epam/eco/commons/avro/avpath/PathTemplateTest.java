/*
 *
 */
package com.epam.eco.commons.avro.avpath;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrei_Tytsik
 */
public class PathTemplateTest {

    @Test
    public void testRecognizedAsElementSelector() throws Exception {
        Assert.assertFalse(PathTemplate.with(null, null).isElementSelector());
        Assert.assertFalse(PathTemplate.with("/job", null).isElementSelector());
        Assert.assertFalse(PathTemplate.with("/hobby[*]/description", null).isElementSelector());
        Assert.assertTrue(PathTemplate.with("/hobby[*]", null).isElementSelector());
    }

    @Test
    public void testFormattedToPlainPath() throws Exception {
        String plain = PathTemplate.with(null, null).toPlainPath();
        Assert.assertNull(plain);

        plain = PathTemplate.with("", null).toPlainPath();
        Assert.assertEquals("", plain);

        plain = PathTemplate.with("/job", null).toPlainPath();
        Assert.assertEquals("job", plain);

        plain = PathTemplate.with("/job/description", null).toPlainPath();
        Assert.assertEquals("job.description", plain);

        plain = PathTemplate.with("/hobby[*]", null).toPlainPath();
        Assert.assertEquals("hobby", plain);

        plain = PathTemplate.with("/hobby[*]/description", null).toPlainPath();
        Assert.assertEquals("hobby.description", plain);

        plain = PathTemplate.with("/hobby[*]/description[*]", null).toPlainPath();
        Assert.assertEquals("hobby.description", plain);

        plain = PathTemplate.with("/job[*]/description", null).toPlainPath("$");
        Assert.assertEquals("job$description", plain);
    }

}
