---
title: Editing textual runbooks in Azure Automation
description: This article provides different procedures for working with PowerShell and PowerShell Workflow runbooks in Azure Automation using the textual editor.
services: automation
ms.service: automation
ms.component: process-automation
author: georgewallace
ms.author: gwallace
ms.date: 04/02/2018
ms.topic: conceptual
manager: carmonm
---
# Editing textual runbooks in Azure Automation

The textual editor in Azure Automation can be used to edit [PowerShell runbooks](automation-runbook-types.md#powershell-runbooks) and [PowerShell Workflow runbooks](automation-runbook-types.md#powershell-workflow-runbooks). This has the typical features of other code editors such as intellisense and color coding  with additional special features to assist you in accessing resources common to runbooks. This article provides detailed steps for performing different functions with this editor.

The textual editor includes a feature to insert code for cmdlets, assets, and child runbooks into a runbook. Rather than typing in the code yourself, you can select from a list of available resources and have the appropriate code inserted into the runbook.

Each runbook in Azure Automation has two versions, Draft and Published. You edit the Draft version of the runbook and then publish it so it can be executed. The Published version cannot be edited. See [Publishing a runbook](automation-creating-importing-runbook.md#publishing-a-runbook) for more information.

To work with [Graphical Runbooks](automation-runbook-types.md#graphical-runbooks), see [Graphical authoring in Azure Automation](automation-graphical-authoring-intro.md).

## To edit a runbook with the Azure portal

Use the following procedure to open a runbook for editing in the textual editor.

1. In the Azure portal, select your automation account.
2. Under **PROCESS AUTOMATION**, select **Runbooks** to open the list of runbooks.
3. Select the runbook you want to edit and then click the **Edit** button.
4. Perform the required editing.
5. Click **Save** when your edits are complete.
6. Click **Publish** if you want the latest draft version of the runbook to be published.

### To insert a cmdlet into a runbook

1. In the Canvas of the textual editor, position the cursor where you want to place the cmdlet.
2. Expand the **Cmdlets** node in the Library control.
3. Expand the module containing the cmdlet you want to use.
4. Right click the cmdlet to insert and select **Add to canvas**. If the cmdlet has more than one parameter set, then the default set will be added. You can also expand the cmdlet to select a different parameter set.
5. The code for the cmdlet is inserted with its entire list of parameters.
6. Provide an appropriate value in place of the data type surrounded by braces <> for any required parameters. Remove any parameters you don't need.

### To insert code for a child runbook into a runbook

1. In the Canvas of the textual editor, position the cursor where you want to place the code for the [child runbook](automation-child-runbooks.md).
2. Expand the **Runbooks** node in the Library control.
3. Right click the runbook to insert and select **Add to canvas**.
4. The code for the child runbook is inserted with any placeholders for any runbook parameters.
5. Replace the placeholders with appropriate values for each parameter.

### To insert an asset into a runbook

1. In the Canvas of the textual editor, position the cursor where you want to place the code for the child runbook.
2. Expand the **Assets** node in the Library control.
3. Expand the node for the type of asset you want.
4. Right click the asset to insert and select **Add to canvas**. For [variable assets](automation-variables.md), select either **Add "Get Variable" to canvas** or **Add "Set Variable" to canvas** depending on whether you want to get or set the variable.
5. The code for the asset is inserted into the runbook.

## To edit an Azure Automation runbook using Windows PowerShell

To edit a runbook with Windows PowerShell, you use the editor of your choice and save it to a .ps1 file. You can use the [Get-AzureAutomationRunbookDefinition](http://aka.ms/runbookauthor/cmdlet/getazurerunbookdefinition) cmdlet to retrieve the contents of the runbook and then [Set-AzureAutomationRunbookDefinition](http://aka.ms/runbookauthor/cmdlet/setazurerunbookdefinition) cmdlet to replace the existing draft runbook with the modified one.

### To Retrieve the Contents of a Runbook Using Windows PowerShell

The following sample commands show how to retrieve the script for a runbook and save it to a script file. In this example, the Draft version is retrieved. It is also possible to retrieve the Published version of the runbook although this version cannot be changed.

```powershell-interactive
$automationAccountName = "MyAutomationAccount"
$runbookName = "Sample-TestRunbook"
$scriptPath = "c:\runbooks\Sample-TestRunbook.ps1"

$runbookDefinition = Get-AzureAutomationRunbookDefinition -AutomationAccountName $automationAccountName -Name $runbookName -Slot Draft
$runbookContent = $runbookDefinition.Content

Out-File -InputObject $runbookContent -FilePath $scriptPath
```

### To Change the Contents of a Runbook Using Windows PowerShell

The following sample commands show how to replace the existing contents of a runbook with the contents of a script file. Note that this is the same sample procedure as in [To import a runbook from a script file with Windows PowerShell](automation-creating-importing-runbook.md).

```powershell-interactive
$automationAccountName = "MyAutomationAccount"
$runbookName = "Sample-TestRunbook"
$scriptPath = "c:\runbooks\Sample-TestRunbook.ps1"

Set-AzureAutomationRunbookDefinition -AutomationAccountName $automationAccountName -Name $runbookName -Path $scriptPath -Overwrite
Publish-AzureAutomationRunbook ???AutomationAccountName $automationAccountName ???Name $runbookName
```

## Related articles

* [Creating or importing a runbook in Azure Automation](automation-creating-importing-runbook.md)
* [Learning PowerShell workflow](automation-powershell-workflow.md)
* [Graphical authoring in Azure Automation](automation-graphical-authoring-intro.md)
* [Certificates](automation-certificates.md)
* [Connections](automation-connections.md)
* [Credentials](automation-credentials.md)
* [Schedules](automation-schedules.md)
* [Variables](automation-variables.md)
