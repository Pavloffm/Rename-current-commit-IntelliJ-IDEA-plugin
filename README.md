# Rename-current-commit-IntelliJ-IDEA-plugin

**RenameCurrentCommit** is a JetBrains IntelliJ IDEA plugin that allows users to rename the most recent Git commit message directly from the IDE. This provides a seamless way to amend commit messages without using the terminal.

---

## Features

- Rename the most recent commit message with a simple UI prompt.
- Integrated directly into IntelliJ IDEA’s Git features.
- Displays success and error messages for user feedback.

## Usage

1. Open a project with a Git repository.
2. Right-click on a commit in the **Version Control** panel.
3. Select **Rename Current Commit** from the context menu.
4. Enter the new commit message when prompted.
5. Click OK to amend the commit.

## How It Works

1. The plugin retrieves the active Git repository in the current IntelliJ IDEA project.
2. It prompts the user to enter a new commit message.
3. It executes the `git commit --amend -m "new message"` command to modify the last commit.
4. If successful, a confirmation message is displayed. Otherwise, an error is shown.

## Dependencies

- **Git4Idea** – A dependency for integrating Git commands within IntelliJ IDEA.

