package unit;

import captura.domain.ArticleObject;
import captura.exceptions.InvalidArticleObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArticleObjectTest {

    @Test
    @DisplayName("check if null value throw an exception")
    public void checkNullText() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    ArticleObject.of(null);
                });
    }

    @Test
    @DisplayName("check if valid text returns valid instance")
    public void checkValidObject() {
        final var input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        var articleObject = ArticleObject.of(input);
        Assertions.assertEquals(input, articleObject.getText());
    }

    @Test
    @DisplayName("check if given text is invalid for constraint of min length")
    public void checkObjectLessThanMinLength() {
        final var input = "";
        var exception =
                Assertions.assertThrows(
                        InvalidArticleObject.class,
                        () -> {
                            ArticleObject.of(input);
                        });
        var expectedMessage = "Invalid object text";
        var actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("check if given text is invalid for constraint of max length")
    public void checkObjectGreaterThanMaxLength() {
        final var input =
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut eros et nisl sagittis vestibulum. Nullam nulla eros, ultricies sit amet, nonummy id, imperdiet feugiat, pede. Sed lectus. Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque facilisis. Etiam imperdiet imperdiet orci. Nunc nec neque. Phasellus leo dolor, tempus non, auctor et, hendrerit quis, nisi. Curabitur ligula sapien, tincidunt non, euismod vitae, posuere imperdiet, leo. Maecenas malesuada. Praesent congue erat at massa. S";
        var exception =
                Assertions.assertThrows(
                        InvalidArticleObject.class,
                        () -> {
                            ArticleObject.of(input);
                        });
        var expectedMessage = "Invalid object text";
        var actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName(
            "check if parse text to html is removing html tags and replacing double white spaces")
    public void checkObjectStripHtmlTags() {
        final var input =
                "<p>Lorem <b>ipsum</b> dolor sit amet,    consectetur adipiscing elit.</p>";
        var articleObject = ArticleObject.of(input);
        var expectedText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        Assertions.assertEquals(articleObject.getText(), expectedText);
    }
}
