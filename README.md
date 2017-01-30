# kantan.sbt

[![Build Status](https://travis-ci.org/nrinaudo/sbt-glpages.svg?branch=master)](https://travis-ci.org/nrinaudo/sbt-glpages)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nrinaudo/sbt-glpages/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nrinaudo/sbt-glpages)

## Overview

SBT autoplugin to release documentation to [Gitlab Pages](https://docs.gitlab.com/ee/user/project/pages/index.html).

The heavy-lifting is left to [`sbt-ghpages`] - `sbt-glpages` simply sets the correct configuration values for Gitlab.
As such, please consult the [`sbt-ghpages`] documentation for detailed information

## Setup (Git)

`sbt-glpages` requires a dedicated, orphan in your Git repository to work. By default, this will be `gl-pages`, but this
can be changed with the `ghpagesBranch` setting.

Here are the quick steps to creating the `gl-pages` branch:

```bash
# Using a fresh, temporary clone is safest for this procedure
$ pushd /tmp
$ git clone git@github.com:youruser/yourproject.git
$ cd yourproject

# Create branch with no history or content
$ git checkout --orphan gl-pages
$ git rm -rf .

# Establish the branch existence
$ git commit --allow-empty -m "Initialize gl-pages branch"
$ git push origin gh-pages

# Return to original working copy clone, we're finished with the /tmp one
$ popd
$ rm -rf /tmp/yourproject
```

You then need to add a `.gitlab-ci.yml` file that matches the technology you're using to generate your static
documentation. For example, if you're generating a [Jekyll](https://jekyllrb.com) site:

```yml
image: ruby:2.1
pages:
  script:
  - gem install jekyll
  - jekyll build -d public/ --config _config.yml
  artifacts:
    paths:
    - public
```

Note that this file must be added to `gl-pages`, not your main branch.

## Setup (SBT)

You first need to add the `sbt-glpages` plugin to your project by adding the following line to `project/plugins.sbt`:

```scala
addSbtPlugin("com.nrinaudo" %  "sbt-glpages" % "1.0.0")
```

Once that's done, you need to manually enable the plugin by adding the folloing line in your `build.sbt`:

```scala
enablePlugins(GlpagesPlugin)
```

Finally, `sbt-glpages` needs to know the URL of your repository. This is declared through the `git.remoteRepo` in your
`build.sbt` file:

```scala
git.remoteRepo := "ssh://git@my.gitlab.net/user/project.git"
```

## Usage

Under the hood, `sbt-glpages` relies on [`sbt-site`] to generate the site. Running the `makeSite` command will
generate your documentation in `target/site`, which is what `sbt-glpages` will publish.

Use `ghpagesPushSite` to generate the site, grab anything in `target/site` and push it to the `gl-pages` branch,
deleting anything it might have contained previously save for the `.gitlab-ci.yml` file.


[`sbt-ghpages`]:https://github.com/sbt/sbt-ghpages
[`sbt-site`]:https://github.com/sbt/sbt-site
