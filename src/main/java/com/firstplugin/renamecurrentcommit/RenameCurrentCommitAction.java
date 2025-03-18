package com.firstplugin.renamecurrentcommit;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import git4idea.commands.Git;
import git4idea.commands.GitCommand;
import git4idea.commands.GitLineHandler;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;

public class RenameCurrentCommitAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        // Connect to git
        GitRepositoryManager repositoryManager = GitRepositoryManager.getInstance(project);
        GitRepository repository = repositoryManager.getRepositories().stream().findFirst().orElse(null);
        if (repository == null) {
            Messages.showErrorDialog(project, "No Git repository found!", "Error");
            return;
        }

        // Ask for commit message
        String newCommitMessage = Messages.showInputDialog(project, "Enter new commit message:", "Rename Commit", Messages.getQuestionIcon());

        // If user cancels or enters empty message, do nothing
        if (newCommitMessage == null || newCommitMessage.trim().isEmpty()) {
            Messages.showErrorDialog(project, "Commit message cannot be empty!", "Error");
            return;
        }

        // Rename current commit message
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            try {
                GitLineHandler handler = new GitLineHandler(project, repository.getRoot(), GitCommand.COMMIT);
                handler.addParameters("--amend", "-m", newCommitMessage); // Removed "--only"
                Git git = Git.getInstance();
                git.runCommand(handler);

                ApplicationManager.getApplication().invokeLater(() ->
                        Messages.showInfoMessage(project, "Commit message updated successfully!", "Success")
                );
            } catch (Exception ex) {
                ApplicationManager.getApplication().invokeLater(() ->
                        Messages.showErrorDialog(project, "An error occurred: " + ex.getMessage(), "Error")
                );
            }
        });
    }
}
