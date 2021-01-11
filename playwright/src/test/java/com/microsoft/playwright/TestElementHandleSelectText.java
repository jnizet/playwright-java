package com.microsoft.playwright;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class TestElementHandleSelectText extends TestBase {

  @Test
  void shouldSelectTextarea() {
    page.navigate(server.PREFIX + "/input/textarea.html");
    ElementHandle textarea = page.querySelector("textarea");
    textarea.evaluate("textarea => textarea.value = 'some value'");
    textarea.selectText();
    if (isFirefox()) {
      assertEquals(0, textarea.evaluate("el => el.selectionStart"));
      assertEquals(10, textarea.evaluate("el => el.selectionEnd"));
    } else {
      assertEquals("some value", page.evaluate("() => window.getSelection().toString()"));
    }
  }

  @Test
  void shouldSelectInput() {
    page.navigate(server.PREFIX + "/input/textarea.html");
    ElementHandle input = page.querySelector("input");
    input.evaluate("input => input.value = 'some value'");
    input.selectText();
    if (isFirefox()) {
      assertEquals(0, input.evaluate("el => el.selectionStart"));
      assertEquals(10, input.evaluate("el => el.selectionEnd"));
    } else {
      assertEquals("some value", page.evaluate("() => window.getSelection().toString()"));
    }
  }

  @Test
  void shouldSelectPlainDiv() {
    page.navigate(server.PREFIX + "/input/textarea.html");
    ElementHandle div = page.querySelector("div.plain");
    div.selectText();
    assertEquals("Plain div", page.evaluate("() => window.getSelection().toString()"));
  }

  @Test
  void shouldTimeoutWaitingForInvisibleElement() {
    page.navigate(server.PREFIX + "/input/textarea.html");
    ElementHandle textarea = page.querySelector("textarea");
    textarea.evaluate("e => e.style.display = 'none'");
    try {
      textarea.selectText(new ElementHandle.SelectTextOptions().withTimeout(3000));
      fail("did not throw");
    } catch (PlaywrightException e) {
      assertTrue(e.getMessage().contains("element is not visible"));
    }
  }

//  @Test
  void shouldWaitForVisible() {
    // TODO Wait for Async API implementation
  }
}