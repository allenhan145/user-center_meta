# User-Center

## Overview

A user center that allows users to sign up, sign in, and administrate.

– The project was slimmed and abstracted based on Ant Design Pro into a reusable template for
future development on similar projects.

– Modified and encapsulated the Umi-request library, adding universal request interception
and exception handler. Differentiate the runtime environment based on commands, reducing repet
itive codes and increasing maintainability.

– Used MyBatis + MyBatis Plus for the data access layer. While most functions are reused, a
universal operation template is customized, drastically increasing development efficiency.

– Encapsulated a global exception handler to avoid redundant error messages and standardize the
return value of interfaces with self-defined error codes.

– Front-end deployment using Nginx and back-end with Docker.

This project is initialized with [Ant Design Pro](https://pro.ant.design). Follow is the quick guide for how to use.

## Environment prepare

Make sure npm is in the environment. If not, refer to the [npm package manager](http://npmjs.org/).

Once you have npm installed you can run the following both to install and upgrade Yarn:

```powershell
npm install --global yarn
```

## Provided Scripts

Ant Design Pro provides some useful script to help you quick start and build with web project, code style check and test.

Scripts provided in `package.json`. It's safe to modify or add additional script:

### Start project

```bash
npm start
```

### Build project

```bash
npm run build
```

### Check code style

```bash
npm run lint
```

You can also use script to auto fix some lint error:

```bash
npm run lint:fix
```
