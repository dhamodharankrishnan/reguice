package de.skuzzle.inject.conf;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.ProvisionException;

@RunWith(MockitoJUnitRunner.class)
public class StringTextContentTypeTest {

    @Mock
    private TextResource resource;

    private StringTextContentType subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new StringTextContentType();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAString() throws Exception {
        this.subject.createInstance(Integer.class, this.resource);
    }

    @Test
    public void testCreateInstance() throws Exception {
        final Reader reader = new StringReader("foo\nbar");
        when(this.resource.openStream()).thenReturn(reader);

        final String s = this.subject.createInstance(String.class, this.resource);
        assertEquals("foo\nbar", s);
    }

    @Test(expected = ProvisionException.class)
    public void testIOException() throws Exception {
        when(this.resource.openStream()).thenThrow(IOException.class);
        this.subject.createInstance(String.class, this.resource);
    }
}
