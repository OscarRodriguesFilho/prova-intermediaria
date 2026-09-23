param(
    [Parameter(Mandatory)]
    [string]$GitHubRepository,

    [string]$HostName = 'ec2-44-197-175-105.compute-1.amazonaws.com',

    [string]$KeyPath = 'C:\Users\oscar\Documents\insper\agil\.credencias\projsoft26b.pem'
)

$ErrorActionPreference = 'Stop'

if (-not (Get-Command gh -ErrorAction SilentlyContinue)) {
    throw 'GitHub CLI (gh) não foi encontrado.'
}
if (-not (Test-Path -LiteralPath $KeyPath)) {
    throw "Chave SSH não encontrada: $KeyPath"
}

Write-Host 'Instalando Docker e Docker Compose na EC2...'
ssh -o BatchMode=yes -o ConnectTimeout=10 -i $KeyPath "ubuntu@$HostName" `
    "sudo apt-get update && sudo apt-get install -y docker.io docker-compose-v2 && sudo systemctl enable --now docker && sudo usermod -aG docker ubuntu && sudo mkdir -p /home/ubuntu/prova-intermediaria && sudo chown ubuntu:ubuntu /home/ubuntu/prova-intermediaria"

$securePassword = Read-Host 'Senha do PostgreSQL para o deploy' -AsSecureString
$passwordPointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)
try {
    $dbPassword = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($passwordPointer)
    gh secret set HOST_TEST --repo $GitHubRepository --body $HostName
    Get-Content -Raw -LiteralPath $KeyPath | gh secret set KEY_TEST --repo $GitHubRepository
    gh secret set DB_PASSWORD --repo $GitHubRepository --body $dbPassword
}
finally {
    [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($passwordPointer)
}

Write-Host 'Ambiente preparado. Um push na main executará os testes e, se passarem, fará o deploy.'
