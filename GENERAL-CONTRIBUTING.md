# Contributing to RGA innersource projects

A big welcome and thank you for considering contributing to RGA innersource projects!

Reading and following these guidelines will help us make the contribution process easy and effective for everyone involved. It also communicates that you agree to respect the time of the developers managing and developing these innersource projects. In return, we will reciprocate that respect by addressing your issue, assessing changes, and helping you finalize your pull requests.

## Quicklinks

* [Code of Conduct](#code-of-conduct)
* [Getting Started](#getting-started)
  * [Issues](#issues)
  * [Pull Requests](#pull-requests)
* [Artifact Repository](#artifact-repository)
* [Getting Help](#getting-help)
* [Abandoned Projects Policy](#abandoned-projects-policy)

## Code of Conduct

We take our innersource community seriously and hold ourselves and other contributors to high standards of communication. By participating and contributing to this project, you agree to uphold our [Code of Conduct](https://www.rgare.com/docs/default-source/regulatory-documents/rga-code-of-conduct.pdf).

## Getting Started

Contributions are made to this repo via Issues and Pull Requests (PRs). A few general guidelines that cover both:

* Search for existing Issues and PRs before creating your own.
* We work hard to makes sure issues are handled in a timely manner but, depending on the impact, it could take a while to investigate the root cause. A friendly ping in the comment thread to the submitter or a contributor can help draw attention if your issue is blocking.
* If you've never contributed before, see [the first timer's guide](someplace) for resources and tips on how to get started.

### Issues

Issues should be used to report problems with the library, request a new feature, or to discuss potential changes before a PR is created. When you create a new Issue, a template will be loaded that will guide you through collecting and providing the information we need to investigate.

If you find an Issue that addresses the problem you're having, please add your own reproduction information to the existing issue rather than creating a new one. Adding a [reaction](https://github.blog/2016-03-10-add-reactions-to-pull-requests-issues-and-comments/) can also help be indicating to our maintainers that a particular problem is affecting more than just the reporter.

### Pull Requests

PRs to our libraries are always welcome and can be a quick way to get your fix or improvement slated for the next release. In general, PRs should:

* Only fix/add the functionality in question **OR** address wide-spread whitespace/style issues, not both.
* Add unit or integration tests for fixed or changed functionality (if a test suite already exists).
* Address a single concern in the least number of changed lines as possible.
* Include documentation in the repo.
* Be accompanied by a complete Pull Request template (loaded automatically when a PR is created).

For changes that address core functionality or would require breaking changes (e.g. a major release), it's best to open an Issue to discuss your proposal first. This is not required but can save time creating and reviewing changes.

In general, we follow the ["fork-and-pull" Git workflow](https://github.com/susam/gitpr)

1. Fork the repository to your own Github account
2. Clone the project to your machine
3. Create a branch locally with a succinct but descriptive name
4. Commit changes to the branch
5. Following any formatting and testing guidelines specific to this repo
6. Push changes to your fork
7. Open a PR in our repository and follow the PR template so that we can efficiently review the changes.

To facilitate the "fork-and-pull" workflow please create a master branch protection rule for your repo as follows:

1. Go to the project Settings and click on Branches
2. Click the add rule button by the Branch protection rules section
3. Use master as the "Branch name pattern"
4. Under "Rule settings" check "Require pull request reviews before merging" and specify at least 2 approving reviews
5. Check "Dismiss stale pull requests approvals when new commits are pushed"
6. Check "Require review from Code Owners"
7. Check "Restrict who can dismiss pull request reviews" and add your project team
8. Check "Require status checks to pass before merging"
9. Check "Require branches to be up to date before merging"
10. Check "Require signed commits"
11. Check "Include administrators"
12. Check "Restrict who can push to matching branches" and add your team or trusted commiters on your team
13. Save your changes

## Artifact Repository

The Innersource project will use the RGA managed repository [https://nexus.rgare.net](https://nexus.rgare.net) to store and manage artifacts.

## Getting Help

Join us in the #your-innersource-project-slack-channel on [rgare.slack.com](https://rgare.slack.com) and post your question there.

## Abandoned Projects Policy

If for some reason an innersource project owner or team wishes to leave a project, a call for volunteers will be made.  The TALC will step in and make a decision if no volunteers come forward.
