---
title: Azure Automation Windows Hybrid Runbook Worker
description: This article provides information on installing an Azure Automation Hybrid Runbook Worker that you can use to run runbooks on Windows-based computers in your local datacenter or cloud environment.
services: automation
ms.service: automation
ms.component: process-automation
author: georgewallace
ms.author: gwallace
ms.date: 04/25/2018
ms.topic: conceptual
manager: carmonm
---
# Deploy a Windows Hybrid Runbook Worker

You can use the Hybrid Runbook Worker feature of Azure Automation to run runbooks directly on the computer that's hosting the role and against resources in the environment to manage those local resources. Runbooks are stored and managed in Azure Automation and then delivered to one or more designated computers. This article describes how to install the Hybrid Runbook Worker on a Windows machine.

## Installing the Windows Hybrid Runbook Worker

To install and configure a Windows Hybrid Runbook Worker, you can use two methods. The recommended method is using an Automation runbook to completely automate the process of configuring a Windows computer. The second method is following a step-by-step procedure to manually install and configure the role.

> [!NOTE]
> To manage the configuration of your servers that support the Hybrid Runbook Worker role with Desired State Configuration (DSC), you need to add them as DSC nodes.

The minimum requirements for a Windows Hybrid Runbook Worker are:

* Windows Server 2012 or later.
* Windows PowerShell 4.0 or later ([download WMF 4.0](https://www.microsoft.com/download/details.aspx?id=40855)). We recommend Windows PowerShell 5.1 ([download WMF 5.1](https://www.microsoft.com/download/details.aspx?id=54616)) for increased reliability.
* .NET Framework 4.6.2 or later.
* Two cores.
* 4 GB of RAM.
* Port 443 (outbound).

To get more networking requirements for the Hybrid Runbook Worker, see [Configuring your network](automation-hybrid-runbook-worker.md#network-planning).

For more information about onboarding servers for management with DSC, see [Onboarding machines for management by Azure Automation DSC](automation-dsc-onboarding.md).
If you enable the [Update Management solution](../operations-management-suite/oms-solution-update-management.md), any Windows computer that's connected to your Azure Log Analytics workspace is  automatically configured as a Hybrid Runbook Worker to support runbooks included in this solution. However, it isn't registered with any Hybrid Worker groups already defined in your Automation account. 

The computer can be added to a Hybrid Runbook Worker group in your Automation account to support Automation runbooks as long as you're using the same account for both the solution and the Hybrid Runbook Worker group membership. This functionality has been added to version 7.2.12024.0 of the Hybrid Runbook Worker.

After you successfully deploy a runbook worker, review [Run runbooks on a Hybrid Runbook Worker](automation-hrw-run-runbooks.md) to learn how to configure your runbooks to automate processes in your on-premises datacenter or other cloud environment.

### Automated deployment

Perform the following steps to automate the installation and configuration of the Windows Hybrid Worker role:

1. Download the New-OnPremiseHybridWorker.ps1 script from the [PowerShell Gallery](https://www.powershellgallery.com/packages/New-OnPremiseHybridWorker/DisplayScript) directly from the computer running the Hybrid Runbook Worker role or from another computer in your environment. Copy the script to the worker.

   The New-OnPremiseHybridWorker.ps1 script requires the following parameters during execution:

   * *AutomationAccountName* (mandatory): The name of your Automation account.
   * *AAResourceGroupName* (mandatory): The name of the resource group that's associated with your Automation account.
   * *OMSResourceGroupName* (optional): The name of the resource group for the Operations Management Suite workspace. If this resource group is not specified, *AAResourceGroupName* is used.
   * *HybridGroupName* (mandatory): The name of a Hybrid Runbook Worker group that you specify as a target for the runbooks that support this scenario.
   * *SubscriptionID* (mandatory): The Azure subscription ID that your Automation account is in.
   * *WorkspaceName* (optional): The Log Analytics workspace name. If you don't have a Log Analytics workspace, the script creates and configures one.

     > [!NOTE]
     > Currently, the only Automation regions supported for integration with Log Analytics are **Australia Southeast**, **East US 2**, **Southeast Asia**, and **West Europe**. If your Automation account is not in one of those regions, the script creates a Log Analytics workspace but warns you that it can't link them together.

1. On your computer, open **Windows PowerShell** from the **Start** screen in Administrator mode.
1. From the PowerShell command-line shell, browse to the folder that contains the script that you downloaded. Change the values for the parameters *-AutomationAccountName*, *-AAResourceGroupName*, *-OMSResourceGroupName*, *-HybridGroupName*, *-SubscriptionId*, and *-WorkspaceName*. Then run the script.

     > [!NOTE]
     > You're prompted to authenticate with Azure after you run the script. You *must* sign in with an account that's a member of the Subscription Admins role and co-administrator of the subscription.

   ```powershell-interactive
   .\New-OnPremiseHybridWorker.ps1 -AutomationAccountName <NameofAutomationAccount> -AAResourceGroupName <NameofResourceGroup>`
   -OMSResourceGroupName <NameofOResourceGroup> -HybridGroupName <NameofHRWGroup> `
   -SubscriptionId <AzureSubscriptionId> -WorkspaceName <NameOfLogAnalyticsWorkspace>
   ```

1. You're prompted to agree to install NuGet, and you're prompted to authenticate with your Azure credentials.

1. After the script is finished, the **Hybrid Worker Groups** page shows the new group and the number of members. If it's an existing group, the number of members is incremented. You can select the group from the list on the **Hybrid Worker Groups** page and select the **Hybrid Workers** tile. On the **Hybrid Workers** page, you see each member of the group listed.

### Manual deployment

Perform the first two steps once for your Automation environment, and then repeat the remaining steps for each worker computer.

#### 1. Create a Log Analytics workspace

If you don't already have a Log Analytics workspace, create one by using the instructions at [Manage your workspace](../log-analytics/log-analytics-manage-access.md). You can use an existing workspace if you already have one.

#### 2. Add the Automation solution to the Log Analytics workspace

Solutions add functionality to Log Analytics. The Automation solution adds functionality for Azure Automation, including support for Hybrid Runbook Worker. When you add the solution to your workspace, it automatically pushes worker components to the agent computer that you will install in the next step.

To add the **Automation** solution to your Log Analytics workspace, follow the instructions at [To add a solution using the Solutions Gallery](../log-analytics/log-analytics-add-solutions.md).

#### 3. Install the Microsoft Monitoring Agent

The Microsoft Monitoring Agent connects computers to Log Analytics. When you install the agent on your on-premises computer and connect it to your workspace, it automatically downloads the components that are required for Hybrid Runbook Worker.

To install the agent on the on-premises computer, follow the instructions at [Connect Windows computers to Log Analytics](../log-analytics/log-analytics-windows-agent.md). You can repeat this process for multiple computers to add multiple workers to your environment.

When the agent has successfully connected to Log Analytics, it's listed on the **Connected Sources** tab of the Log Analytics **Settings** page. You can verify that the agent has correctly downloaded the Automation solution when it has a folder called **AzureAutomationFiles** in C:\Program Files\Microsoft Monitoring Agent\Agent. To confirm the version of the Hybrid Runbook Worker, you can browse to C:\Program Files\Microsoft Monitoring Agent\Agent\AzureAutomation\ and note the \\*version* subfolder.

#### 4. Install the runbook environment and connect to Azure Automation

When you add an agent to Log Analytics, the Automation solution pushes down the **HybridRegistration** PowerShell module, which contains the **Add-HybridRunbookWorker** cmdlet. You use this cmdlet to install the runbook environment on the computer and register it with Azure Automation.

Open a PowerShell session in Administrator mode and run the following commands to import the module:

```powershell-interactive
cd "C:\Program Files\Microsoft Monitoring Agent\Agent\AzureAutomation\<version>\HybridRegistration"
Import-Module HybridRegistration.psd1
```

Then run the **Add-HybridRunbookWorker** cmdlet by using the following syntax:

```powershell-interactive
Add-HybridRunbookWorker ???GroupName <String> -EndPoint <Url> -Token <String>
```

You can get the information required for this cmdlet from the **Manage Keys** page in the Azure portal. Open this page by selecting the **Keys** option from the **Settings** page in your Automation account.

!["Manage Keys" page](media/automation-hybrid-runbook-worker/elements-panel-keys.png)

* **GroupName** is the name of the Hybrid Runbook Worker group. If this group already exists in the Automation account, the current computer is added to it. If this group doesn't exist, it's added.
* **EndPoint** is the **URL** entry on the **Manage Keys** page.
* **Token** is the **PRIMARY ACCESS KEY** entry on the **Manage Keys** page.

To receive detailed information about the installation, use the **-Verbose** switch with **Add-HybridRunbookWorker**.

#### 5. Install PowerShell modules

Runbooks can use any of the activities and cmdlets defined in the modules that are installed in your Azure Automation environment. These modules are not automatically deployed to on-premises computers, so you must install them manually. The exception is the Azure module, which is installed by default and provides access to cmdlets for all Azure services and activities for Azure Automation.

Because the primary purpose of the Hybrid Runbook Worker feature is to manage local resources, you most likely need to install the modules that support these resources. For information on installing Windows PowerShell modules, see [Installing Modules](http://msdn.microsoft.com/library/dd878350.aspx). 

Modules that are installed must be in a location referenced by the **PSModulePath** environment variable so that the hybrid worker can automatically import them. For more information, see [Modifying the PSModulePath Installation Path](https://msdn.microsoft.com/library/dd878326%28v=vs.85%29.aspx).

## Troubleshooting

The Hybrid Runbook Worker depends on the Microsoft Monitoring Agent to communicate with your Automation account to register the worker, receive runbook jobs, and report status. If registration of the worker fails, here are some possible causes for the error.

### The Microsoft Monitoring Agent isn't running

If the Microsoft Monitoring Agent Windows service isn't running, the Hybrid Runbook Worker can't communicate with Azure Automation. Verify that the agent is running by entering the following command in PowerShell: `Get-Service healthservice`. If the service is stopped, enter the following command in PowerShell to start the service: `Start-Service healthservice`.

### Event 4502 appears in the Operations Manager log

In the **Application and Services Logs\Operations Manager** event log, you see event 4502 and EventMessage containing **Microsoft.EnterpriseManagement.HealthService.AzureAutomation.HybridAgent** with the following description: *The certificate presented by the service \<wsid\>.oms.opinsights.azure.com was not issued by a certificate authority used for Microsoft services. Please contact your network administrator to see if they are running a proxy that intercepts TLS/SSL communication. The article KB3126513 has additional troubleshooting information for connectivity issues.*

This event can be caused by your proxy or network firewall blocking communication to Microsoft Azure. Verify that the computer has outbound access to *.azure-automation.net on port 443.

Logs are stored locally on each hybrid worker at C:\ProgramData\Microsoft\System Center\Orchestrator\7.2\SMA\Sandboxes. You can check if there are any warning or error events written to the **Application and Services Logs\Microsoft-SMA\Operations** and **Application and Services Logs\Operations Manager** event log. Look for events that would indicate a connectivity or other issue that's affecting onboarding of the role to Azure Automation, or an issue while performing normal operations.

For additional steps on how to troubleshoot issues with Update Management, see [Update Management: troubleshooting](automation-update-management.md#troubleshooting).

## Next steps

* To learn how to configure your runbooks to automate processes in your on-premises datacenter or other cloud environment, see [Run runbooks on a Hybrid Runbook Worker](automation-hrw-run-runbooks.md).
* For instructions on how to remove Hybrid Runbook Workers, see [Remove Azure Automation Hybrid Runbook Workers](automation-hybrid-runbook-worker.md#remove-a-hybrid-runbook-worker).