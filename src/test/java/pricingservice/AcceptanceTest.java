package pricingservice;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.StoryReporterBuilder;

import io.qameta.allure.jbehave.AllureJbehave;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static java.util.stream.Collectors.toList;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;

public abstract class AcceptanceTest extends JUnitStory {


	@SuppressWarnings("deprecation")
	public AcceptanceTest() {
		Embedder embedder = configuredEmbedder();
		embedder.useMetaFilters(getMetaFilters());
		embedder
		.embedderControls()
		.doVerboseFailures(true)
		.useStoryTimeoutInSecs(60);
	}

	@Override
	public Configuration configuration() {
		final CrossReference xref = new CrossReference();
		Properties viewResources = new Properties();
		viewResources.setProperty("decorateNonHtml", "false");
		return new MostUsefulConfiguration()
				.useStoryReporterBuilder(new StoryReporterBuilder()
						.withDefaultFormats().withFormats(CONSOLE, HTML, TXT)
						.withViewResources(viewResources)
						.withCrossReference(xref)
						.withKeywords(new LocalizedKeywords(Locale.ENGLISH))
						.withCodeLocation(CodeLocations.codeLocationFromPath("build/jbehave"))
						.withReporters(new AllureJbehave())
						)
						
				.useViewGenerator(new FreemarkerViewGenerator());
	}

	private List<String> getMetaFilters() {
		String metaFiltersProperty = System.getProperty("metaFilters", "");
		return Arrays
				.stream(metaFiltersProperty
				.split(","))
				.map(String::trim)
				.collect(toList());
	}
}
