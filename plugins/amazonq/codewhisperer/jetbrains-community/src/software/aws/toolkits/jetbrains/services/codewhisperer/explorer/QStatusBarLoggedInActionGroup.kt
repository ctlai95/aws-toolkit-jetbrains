// Copyright 2023 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package software.aws.toolkits.jetbrains.services.codewhisperer.explorer

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.project.Project
import software.aws.toolkits.jetbrains.core.credentials.AwsBearerTokenConnection
import software.aws.toolkits.jetbrains.core.credentials.ToolkitConnectionManager
import software.aws.toolkits.jetbrains.core.credentials.actions.SsoLogoutAction
import software.aws.toolkits.jetbrains.core.credentials.pinning.QConnection
import software.aws.toolkits.jetbrains.core.credentials.sono.isSono
import software.aws.toolkits.jetbrains.services.amazonq.profile.QRegionProfileManager
import software.aws.toolkits.jetbrains.services.codewhisperer.actions.CodeWhispererConnectOnGithubAction
import software.aws.toolkits.jetbrains.services.codewhisperer.actions.CodeWhispererLearnMoreAction
import software.aws.toolkits.jetbrains.services.codewhisperer.actions.CodeWhispererProvideFeedbackAction
import software.aws.toolkits.jetbrains.services.codewhisperer.codescan.actions.CodeWhispererCodeScanRunAction
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.ActionProvider
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.Customize
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.Learn
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.OpenCodeReference
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.Pause
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.PauseCodeScans
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.Resume
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.ResumeCodeScans
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.buildActionListForCodeScan
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.buildActionListForConnectHelp
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.buildActionListForInlineSuggestions
import software.aws.toolkits.jetbrains.services.codewhisperer.explorer.actions.buildActionListForOtherFeatures
import software.aws.toolkits.resources.message

class QStatusBarLoggedInActionGroup : DefaultActionGroup() {
    private val actionProvider = object : ActionProvider<AnAction> {
        override val pause = Pause()
        override val resume = Resume()
        override val openCodeReference = OpenCodeReference()
        override val customize = Customize()
        override val learn = Learn()
        override val openChatPanel = ActionManager.getInstance().getAction("q.openchat")
        override val runScan = CodeWhispererCodeScanRunAction()
        override val pauseAutoScans = PauseCodeScans()
        override val resumeAutoScans = ResumeCodeScans()
        override val sendFeedback = CodeWhispererProvideFeedbackAction()
        override val connectOnGithub = CodeWhispererConnectOnGithubAction()
        override val documentation = CodeWhispererLearnMoreAction()
    }

    override fun getChildren(e: AnActionEvent?) = e?.project?.let {
        val isPendingActiveProfile = QRegionProfileManager.getInstance().hasValidConnectionButNoActiveProfile(it)
        val actionManager = ActionManager.getInstance()
        buildList {
            if (!isPendingActiveProfile) {
                addAll(buildActionListForActiveProfileSelected(it, actionProvider))
            }

            add(Separator.create())
            add(Separator.create(message("codewhisperer.statusbar.sub_menu.connect_help.title")))
            addAll(buildActionListForConnectHelp(actionProvider))

            add(Separator.create())
            add(actionManager.getAction("codewhisperer.settings"))

            val connection = ToolkitConnectionManager.getInstance(it).activeConnectionForFeature(QConnection.getInstance()) as? AwsBearerTokenConnection

            if (connection != null) {
                if (!connection.isSono()) {
                    add(actionManager.getAction("codewhisperer.switchProfiles"))
                } else {
                    add(actionManager.getAction("q.manage.subscription"))
                }

                add(SsoLogoutAction(connection))
            }
        }.toTypedArray()
    }.orEmpty()

    private fun buildActionListForActiveProfileSelected(
        project: Project,
        actionProvider: ActionProvider<AnAction>,
    ): List<AnAction> = buildList {
        add(Separator.create())
        add(Separator.create(message("codewhisperer.statusbar.sub_menu.inline.title")))
        addAll(buildActionListForInlineSuggestions(project, actionProvider))

        add(Separator.create())
        add(Separator.create(message("codewhisperer.statusbar.sub_menu.security_scans.title")))
        addAll(buildActionListForCodeScan(project, actionProvider))

        add(Separator.create())
        add(Separator.create(message("codewhisperer.statusbar.sub_menu.other_features.title")))
        addAll(buildActionListForOtherFeatures(project, actionProvider))
    }
}
