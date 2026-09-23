param(
    [Parameter(Mandatory)]
    [string]$GitHubRepository,

    [string]$HostName = 'ec2-44-197-175-105.compute-1.amazonaws.com',

    [string]$KeyPath = 'C:\Users\oscar\Documents\insper\agil\.credencias\projsoft26b.pem'
)

$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot

if (-not (Get-Command gh -ErrorAction SilentlyContinue)) {
    throw 'GitHub CLI (gh) não foi encontrado.'
}

gh auth status | Out-Null

Push-Location $projectRoot
try {
    if (-not (Test-Path -LiteralPath '.git')) {
        git init --initial-branch=main
        git add .
        git commit -m 'chore: estrutura inicial da API de produtos'
    }

    if ((git remote) -notcontains 'origin') {
        gh repo create $GitHubRepository --private --source . --remote origin
    }

    & "$PSScriptRoot\bootstrap-aws.ps1" -GitHubRepository $GitHubRepository -HostName $HostName -KeyPath $KeyPath
    git push --set-upstream origin main
}
finally {
    Pop-Location
}
