import { useState } from 'react'
import toast from 'react-hot-toast'
import { History } from 'lucide-react'
import { Card } from '../components/ui/Card'
import { Button } from '../components/ui/Button'
import { Input } from '../components/ui/Input'
import { DataTable } from '../components/ui/DataTable'
import { LoadingState } from '../components/ui/LoadingState'
import { PageHeader } from '../components/common/PageHeader'
import { transactionService } from '../services/transactionService'
import { formatCurrency, formatDateTime } from '../lib/formatters'

export function TransactionsPage() {
  const [accountNumber, setAccountNumber] = useState('')
  const [transactions, setTransactions] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const columns = [
    { key: 'id', header: 'ID' },
    { key: 'senderAccountNumber', header: 'Cuenta origen' },
    { key: 'receiverAccountNumber', header: 'Cuenta destino' },
    {
      key: 'amount',
      header: 'Monto',
      render: (row) => <span className="font-semibold text-brand-700">{formatCurrency(row.amount)}</span>,
    },
    {
      key: 'timestamp',
      header: 'Fecha y hora',
      render: (row) => formatDateTime(row.timestamp),
    },
  ]

  const handleSearch = async (event) => {
    event.preventDefault()

    if (!accountNumber.trim()) {
      setError('Debes ingresar un numero de cuenta')
      return
    }

    try {
      setLoading(true)
      setError('')
      const data = await transactionService.getAccountTransactions(accountNumber.trim())
      setTransactions(data)
      if (data.length === 0) {
        toast('No hay movimientos para esta cuenta')
      }
    } catch (err) {
      setTransactions([])
      setError(err.message)
      toast.error(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <section>
      <PageHeader
        title="Historico de transacciones"
        description="Consulta todos los movimientos de una cuenta especifica y revisa fecha, origen, destino y monto transferido."
      />

      <Card className="mb-5">
        <div className="mb-4 flex items-center gap-3">
          <span className="inline-flex h-11 w-11 items-center justify-center rounded-xl bg-brand-100 text-brand-700">
            <History size={20} />
          </span>
          <div>
            <h2 className="font-display text-xl font-bold text-ink">Buscar movimientos por cuenta</h2>
            <p className="text-sm text-slate-500">Ejemplo de cuenta: ACC1001</p>
          </div>
        </div>

        <form onSubmit={handleSearch} className="flex flex-col gap-3 md:flex-row md:items-end">
          <div className="w-full md:max-w-md">
            <Input
              label="Numero de cuenta"
              placeholder="ACC1001"
              value={accountNumber}
              onChange={(e) => setAccountNumber(e.target.value)}
              error={error}
            />
          </div>
          <Button type="submit" disabled={loading}>
            {loading ? 'Consultando...' : 'Consultar historico'}
          </Button>
        </form>
      </Card>

      <Card>
        {loading ? <LoadingState label="Consultando transacciones..." /> : null}
        {!loading ? (
          <DataTable
            columns={columns}
            rows={transactions}
            emptyLabel="No existen movimientos para la cuenta consultada."
          />
        ) : null}
      </Card>
    </section>
  )
}
