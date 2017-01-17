package krasa.grepconsole.integration;

import krasa.grepconsole.filter.GrepHighlightingInputFilter;
import krasa.grepconsole.plugin.GrepConsoleApplicationComponent;
import krasa.grepconsole.plugin.ServiceManager;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.filters.ConsoleInputFilterExProvider;
import com.intellij.execution.filters.InputFilterEx;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;

public class GrepConsoleInputFilterProvider extends ConsoleInputFilterExProvider {


	protected GrepHighlightingInputFilter createHighlightingFilter(@NotNull Project project, ServiceManager manager) {
		if (GrepConsoleApplicationComponent.getInstance().getState().isSynchronousHighlighting()) {
			return manager.createHighlightInputFilter(project);
		}
		return null;
	}

	@NotNull
	@Override
	public InputFilterEx[] getInputFilters(@NotNull ConsoleView consoleView, @NotNull Project project,
			@NotNull GlobalSearchScope globalSearchScope) {
		ServiceManager manager = ServiceManager.getInstance();
		return new InputFilterEx[] { manager.createCopyingFilter(project), manager.createInputFilter(project) };

	}

}
