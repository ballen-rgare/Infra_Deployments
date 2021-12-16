# Contribution

Please read [RGA's innersource contribution guidelines](https://github.com/rgare/innersource-project-template/blob/master/GENERAL-CONTRIBUTING.md).

## Documentation

- PR for docs site update, if needed
- Code-level documentation expectations
  - 100% documentation coverage for PRs
  - Include links to relevant doc pages

## Assets compilation

Information about compiling CSS, JS, SVG, etc.

## Environment setup

Link to [README installation](README.md#installation) steps and include anything additional needed to contribute to the project.

## Testing

### Adding tests

General information about the test suite and how to format and structure tests.

### Running tests

Any additional information needed to run the test suite. Include `bash`-formatted commands like:

```bash
go test ./...
gradle test
npm test
```

Also include any information about essential manual tests.

## Code quality tools

Information about scripts to run before committing.

## CI Information

What CI checks for and how to pass.

## Artifact Repository

Where artifact is located in the [RGARE nexus repo](https://nexus.rgare.net) and how to retrieve it.  For example, your .npmrc needs to have the following entry:

```bash
registry=https://nexus.rgare.net/repository/npm-all
```

or

```bash
docker pull nexus.rgare.net:4443/some-image:some-tag
npm install some-rga-package
```

## Repo-specific PR guidelines

Anything not covered in the general guidelines linked above.
