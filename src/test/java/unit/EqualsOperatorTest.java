package unit;

import busca.application.impl.EqualsOperator;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsOperatorTest {

    @Test
    @DisplayName("when not given affirmations and denials must concatenate only word")
    public void testOnlyPhrase() {
        final var word = "foo";
        var operator = new EqualsOperator(new ArrayList<>(), new ArrayList<>(), word);
        var output = operator.getElasticQuery();
        var json = new JSONObject(output.toString()).getJSONObject("bool").getJSONArray("should");
        var root = new JSONObject(json.get(0).toString());
        assertEquals(1, json.length());
        assertTrue(root.has("match_phrase"));
        root = root.getJSONObject("match_phrase").getJSONObject("content");
        assertEquals("foo", root.getString("query"));
        assertEquals(10, root.getInt("slop"));
    }

    @Test
    @DisplayName("giving phrase and multiple affirmations")
    public void testPhraseAndAffirmations() {
        final var word = "foo";
        final var affirmations = Arrays.asList("baz", "bar");
        var operator = new EqualsOperator(affirmations, new ArrayList<>(), word);
        var output = operator.getElasticQuery();
        var json = new JSONObject(output.toString()).getJSONObject("bool").getJSONArray("should");
        var firstCondition = new JSONObject(json.get(0).toString());
        var secondCondition = new JSONObject(json.get(1).toString());
        assertEquals(2, json.length());
        assertTrue(firstCondition.has("match_phrase"));
        assertTrue(secondCondition.has("match_phrase"));
        firstCondition = firstCondition.getJSONObject("match_phrase").getJSONObject("content");
        secondCondition = secondCondition.getJSONObject("match_phrase").getJSONObject("content");
        assertEquals("foo baz", firstCondition.getString("query"));
        assertEquals(10, firstCondition.getInt("slop"));
        assertEquals("foo bar", secondCondition.getString("query"));
        assertEquals(10, secondCondition.getInt("slop"));
    }

    @Test
    @DisplayName("giving phrase and multiple affirmations & denials")
    public void testPhraseAffirmationsAndDenials() {
        final var word = "foo";
        final var affirmations = Arrays.asList("baz", "bar");
        final var denials = Arrays.asList("fizz", "buzz");
        var operator = new EqualsOperator(affirmations, denials, word);
        var output = operator.getElasticQuery();
        var affirmationsObject = new JSONObject(output.toString()).getJSONObject("bool").getJSONArray("should");
        var denialsObject = new JSONObject(output.toString()).getJSONObject("bool").getJSONArray("must_not");
        var firstConditionAffirmation = new JSONObject(affirmationsObject.get(0).toString());
        var secondConditionAffirmation = new JSONObject(affirmationsObject.get(1).toString());
        var firstConditionDenial = new JSONObject(denialsObject.get(0).toString());
        var secondConditionDenial = new JSONObject(denialsObject.get(1).toString());
        // affirmations
        assertEquals(2, affirmationsObject.length());
        assertTrue(firstConditionAffirmation.has("match_phrase"));
        assertTrue(secondConditionAffirmation.has("match_phrase"));
        firstConditionAffirmation = firstConditionAffirmation.getJSONObject("match_phrase").getJSONObject("content");
        secondConditionAffirmation = secondConditionAffirmation.getJSONObject("match_phrase").getJSONObject("content");
        assertEquals("foo baz", firstConditionAffirmation.getString("query"));
        assertEquals(10, firstConditionAffirmation.getInt("slop"));
        assertEquals("foo bar", secondConditionAffirmation.getString("query"));
        assertEquals(10, secondConditionAffirmation.getInt("slop"));
        //denials
        assertEquals(2, denialsObject.length());
        assertTrue(firstConditionDenial.has("match_phrase"));
        assertTrue(secondConditionDenial.has("match_phrase"));
        firstConditionDenial = firstConditionDenial.getJSONObject("match_phrase").getJSONObject("content");
        secondConditionDenial = secondConditionDenial.getJSONObject("match_phrase").getJSONObject("content");
        assertEquals("foo fizz", firstConditionDenial.getString("query"));
        assertEquals(10, firstConditionDenial.getInt("slop"));
        assertEquals("foo buzz", secondConditionDenial.getString("query"));
        assertEquals(10, secondConditionDenial.getInt("slop"));
    }

    @Test
    @DisplayName("when not given phrase should throw an exception")
    public void testPhraseNull() {
        assertThrows(NullPointerException.class, () -> new EqualsOperator(new ArrayList<>(), new ArrayList<>(), null));
    }
}
