package krasa.grepconsole.integration;

import org.jetbrains.annotations.NotNull;

import com.intellij.execution.filters.ConsoleInputFilterProvider;
import com.intellij.execution.filters.InputFilter;
import com.intellij.openapi.project.Project;

import krasa.grepconsole.plugin.ServiceManager;

public class GrepConsoleInputFilterProvider implements ConsoleInputFilterProvider {

	@NotNull
	@Override
	public InputFilter[] getDefaultFilters(@NotNull Project project) {
		return new InputFilter[] { ServiceManager.getInstance().createInputFilter(project),
				ServiceManager.getInstance().createAnsiFilter(project),
				ServiceManager.getInstance().createCopyingFilter(project) };
	}

}
