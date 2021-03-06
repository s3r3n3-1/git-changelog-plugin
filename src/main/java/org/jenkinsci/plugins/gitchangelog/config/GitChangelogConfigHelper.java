package org.jenkinsci.plugins.gitchangelog.config;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.logging.Level.SEVERE;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_DATEFORMAT;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_FILE;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_GITHUB_ISSUE_PATTERN;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_IGNORE_COMMITS_REGEXP;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_JIRA_ISSUE_PATTEN;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_NO_ISSUE_NAME;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_READABLE_TAG_NAME;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_TIMEZONE;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.DEFAULT_UNTAGGED_NAME;
import static se.bjurr.gitchangelog.api.GitChangelogApiConstants.ZERO_COMMIT;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.io.CharStreams;

public class GitChangelogConfigHelper {
 private static Logger logger = Logger.getLogger(GitChangelogConfigHelper.class.getName());

 public enum FROMTYPE {
  ref(null), commit(null), firstCommit(ZERO_COMMIT), master("master");
  private final String reference;

  FROMTYPE(String ref) {
   this.reference = ref;
  }

  public String getReference() {
   return reference;
  }
 }

 public static GitChangelogConfig createNewConfig() {
  GitChangelogConfig config = new GitChangelogConfig();
  config.setConfigFile(DEFAULT_FILE);
  config.setDateFormat(DEFAULT_DATEFORMAT);
  config.setSubDirectory("");
  config.setFile("CHANGELOG.html");
  config.setIgnoreCommitsIfMessageMatches(DEFAULT_IGNORE_COMMITS_REGEXP);
  config.setJiraIssuePattern(DEFAULT_JIRA_ISSUE_PATTEN);
  config.setGitHubIssuePattern(DEFAULT_GITHUB_ISSUE_PATTERN);
  config.setNoIssueName(DEFAULT_NO_ISSUE_NAME);
  config.setReadableTagName(DEFAULT_READABLE_TAG_NAME);
  config.setTimeZone(DEFAULT_TIMEZONE);
  config.setUntaggedName(DEFAULT_UNTAGGED_NAME);
  config.setCreateFileTemplateContent(getResourceAsString("fileTemplateDefault.mustache"));
  config.setShowSummaryTemplateContent(getResourceAsString("summaryTemplateDefault.mustache"));
  config.setMediaWikiTemplateContent(getResourceAsString("mediaWikiTemplateDefault.mustache"));

  List<CustomIssue> customIssues = newArrayList();
  customIssues.add(new CustomIssue("", "", "", ""));
  customIssues.add(new CustomIssue("", "", "", ""));
  config.setCustomIssues(customIssues);
  
  return config;
 }

 private static String getResourceAsString(String filename) {
  try {
   return CharStreams.toString(new InputStreamReader(GitChangelogConfigHelper.class.getResourceAsStream(filename)));
  } catch (IOException e) {
   logger.log(SEVERE, "", e);
   return "";
  }
 }
}
