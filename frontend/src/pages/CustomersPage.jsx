import { useEffect, useMemo, useState } from 'react'
import { CircleDollarSign, Users } from 'lucide-react'
import toast from 'react-hot-toast'
import { Card } from '../components/ui/Card'
import { Button } from '../components/ui/Button'
import { Input } from '../components/ui/Input'
import { DataTable } from '../components/ui/DataTable'
import { LoadingState } from '../components/ui/LoadingState'
import { PageHeader } from '../components/common/PageHeader'
import { StatCard } from '../components/common/StatCard'
import { customerService } from '../services/customerService'
import { useAsyncTask } from '../hooks/useAsyncTask'
import { formatCurrency } from '../lib/formatters'

const defaultForm = {
  firstName: '',
  lastName: '',
  accountNumber: '',
  balance: '',
}

export function CustomersPage() {
  const customersTask = useAsyncTask([])
  const [searchAccount, setSearchAccount] = useState('')
  const [form, setForm] = useState(defaultForm)

  const fetchCustomers = async () => {
    await customersTask.run(() => customerService.getCustomers())
  }

  useEffect(() => {
    fetchCustomers()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const totalBalance = useMemo(() => {
    return (customersTask.data || []).reduce((acc, customer) => acc + Number(customer.balance || 0), 0)
  }, [customersTask.data])

  const columns = [
    { key: 'id', header: 'ID' },
    {
      key: 'fullName',
      header: 'Nombre completo',
      render: (row) => `${row.firstName} ${row.lastName}`,
    },
    { key: 'accountNumber', header: 'Numero de cuenta' },
    {
      key: 'balance',
      header: 'Saldo',
      render: (row) => <span className="font-semibold text-emerald-700">{formatCurrency(row.balance)}</span>,
    },
  ]

  const handleSearch = async () => {
    if (!searchAccount.trim()) {
      fetchCustomers()
      return
    }

    try {
      const customer = await customerService.getCustomerByAccountNumber(searchAccount.trim())
      customersTask.setData([customer])
    } catch (err) {
      toast.error(err.message)
      customersTask.setData([])
    }
  }

  const handleCreateCustomer = async (event) => {
    event.preventDefault()

    if (!form.firstName || !form.lastName || !form.accountNumber || !form.balance) {
      toast.error('Completa todos los campos para crear el cliente')
      return
    }

    try {
      await customerService.createCustomer({
        ...form,
        balance: Number(form.balance),
      })
      toast.success('Cliente creado correctamente')
      setForm(defaultForm)
      fetchCustomers()
    } catch (err) {
      toast.error(err.message)
    }
  }

  return (
    <section>
      <PageHeader
        title="Panel de Clientes"
        description="Consulta cuentas registradas, revisa saldos acumulados y registra nuevos clientes en segundos."
      />

      <div className="mb-6 grid gap-4 md:grid-cols-2">
        <StatCard label="Total de clientes" value={customersTask.data.length} icon={Users} />
        <StatCard
          label="Saldo acumulado"
          value={formatCurrency(totalBalance)}
          icon={CircleDollarSign}
          tone="emerald"
        />
      </div>

      <div className="grid gap-5 lg:grid-cols-[1.1fr_2fr]">
        <Card>
          <h2 className="mb-4 font-display text-xl font-bold text-ink">Crear cliente</h2>
          <form onSubmit={handleCreateCustomer} className="space-y-3">
            <Input
              label="Nombres"
              value={form.firstName}
              onChange={(e) => setForm((prev) => ({ ...prev, firstName: e.target.value }))}
            />
            <Input
              label="Apellidos"
              value={form.lastName}
              onChange={(e) => setForm((prev) => ({ ...prev, lastName: e.target.value }))}
            />
            <Input
              label="Numero de cuenta"
              value={form.accountNumber}
              onChange={(e) => setForm((prev) => ({ ...prev, accountNumber: e.target.value }))}
            />
            <Input
              label="Saldo inicial"
              type="number"
              min="0"
              step="0.01"
              value={form.balance}
              onChange={(e) => setForm((prev) => ({ ...prev, balance: e.target.value }))}
            />
            <Button type="submit" className="w-full">
              Crear cliente
            </Button>
          </form>
        </Card>

        <Card>
          <div className="mb-4 flex flex-col gap-3 md:flex-row md:items-end">
            <div className="w-full">
              <Input
                label="Buscar por numero de cuenta"
                placeholder="Ej. ACC1001"
                value={searchAccount}
                onChange={(e) => setSearchAccount(e.target.value)}
              />
            </div>
            <div className="flex gap-2">
              <Button onClick={handleSearch}>Buscar</Button>
              <Button variant="secondary" onClick={fetchCustomers}>
                Limpiar
              </Button>
            </div>
          </div>

          {customersTask.loading ? <LoadingState label="Consultando clientes..." /> : null}
          {customersTask.error ? <p className="mb-3 text-sm font-medium text-rose-600">{customersTask.error}</p> : null}

          {!customersTask.loading ? (
            <DataTable
              columns={columns}
              rows={customersTask.data}
              emptyLabel="No se encontraron clientes para los filtros actuales."
            />
          ) : null}
        </Card>
      </div>
    </section>
  )
}
