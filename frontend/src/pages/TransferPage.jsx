import { useState } from 'react'
import { ArrowLeftRight, BadgeDollarSign } from 'lucide-react'
import toast from 'react-hot-toast'
import { Card } from '../components/ui/Card'
import { Button } from '../components/ui/Button'
import { Input } from '../components/ui/Input'
import { PageHeader } from '../components/common/PageHeader'
import { formatCurrency, formatDateTime } from '../lib/formatters'
import { transactionService } from '../services/transactionService'

const initialForm = {
  senderAccountNumber: '',
  receiverAccountNumber: '',
  amount: '',
}

export function TransferPage() {
  const [form, setForm] = useState(initialForm)
  const [errors, setErrors] = useState({})
  const [loading, setLoading] = useState(false)
  const [latestTransfer, setLatestTransfer] = useState(null)

  const validate = () => {
    const nextErrors = {}

    if (!form.senderAccountNumber.trim()) {
      nextErrors.senderAccountNumber = 'La cuenta origen es obligatoria'
    }
    if (!form.receiverAccountNumber.trim()) {
      nextErrors.receiverAccountNumber = 'La cuenta destino es obligatoria'
    }
    if (form.senderAccountNumber.trim() && form.receiverAccountNumber.trim() && form.senderAccountNumber.trim() === form.receiverAccountNumber.trim()) {
      nextErrors.receiverAccountNumber = 'La cuenta destino debe ser diferente'
    }
    if (!form.amount || Number(form.amount) <= 0) {
      nextErrors.amount = 'El monto debe ser mayor a 0'
    }

    setErrors(nextErrors)
    return Object.keys(nextErrors).length === 0
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    if (!validate()) {
      return
    }

    try {
      setLoading(true)
      const response = await transactionService.transferMoney({
        senderAccountNumber: form.senderAccountNumber.trim(),
        receiverAccountNumber: form.receiverAccountNumber.trim(),
        amount: Number(form.amount),
      })

      setLatestTransfer(response)
      setForm(initialForm)
      setErrors({})
      toast.success('Transferencia realizada con exito')
    } catch (err) {
      toast.error(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <section>
      <PageHeader
        title="Transferencias de dinero"
        description="Realiza transferencias entre cuentas de forma segura con validaciones en tiempo real y confirmacion visual inmediata."
      />

      <div className="grid gap-5 lg:grid-cols-[1.2fr_1fr]">
        <Card>
          <div className="mb-5 flex items-center gap-3">
            <span className="inline-flex h-11 w-11 items-center justify-center rounded-xl bg-brand-100 text-brand-700">
              <ArrowLeftRight size={20} />
            </span>
            <div>
              <h2 className="font-display text-xl font-bold text-ink">Nueva transferencia</h2>
              <p className="text-sm text-slate-500">Completa los campos para mover fondos entre cuentas.</p>
            </div>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            <Input
              label="Cuenta origen"
              placeholder="ACC1001"
              value={form.senderAccountNumber}
              error={errors.senderAccountNumber}
              onChange={(e) => setForm((prev) => ({ ...prev, senderAccountNumber: e.target.value }))}
            />
            <Input
              label="Cuenta destino"
              placeholder="ACC1002"
              value={form.receiverAccountNumber}
              error={errors.receiverAccountNumber}
              onChange={(e) => setForm((prev) => ({ ...prev, receiverAccountNumber: e.target.value }))}
            />
            <Input
              label="Monto"
              type="number"
              min="0.01"
              step="0.01"
              placeholder="0.00"
              value={form.amount}
              error={errors.amount}
              onChange={(e) => setForm((prev) => ({ ...prev, amount: e.target.value }))}
            />

            <Button type="submit" className="w-full" disabled={loading}>
              {loading ? 'Procesando...' : 'Enviar transferencia'}
            </Button>
          </form>
        </Card>

        <Card>
          <div className="mb-5 flex items-center gap-3">
            <span className="inline-flex h-11 w-11 items-center justify-center rounded-xl bg-emerald-100 text-emerald-700">
              <BadgeDollarSign size={20} />
            </span>
            <div>
              <h3 className="font-display text-xl font-bold text-ink">Ultima operacion</h3>
              <p className="text-sm text-slate-500">Resumen de la transferencia mas reciente.</p>
            </div>
          </div>

          {latestTransfer ? (
            <div className="space-y-2 rounded-xl border border-emerald-200 bg-emerald-50 p-4 text-sm">
              <p>
                <span className="font-semibold">ID:</span> {latestTransfer.id}
              </p>
              <p>
                <span className="font-semibold">Origen:</span> {latestTransfer.senderAccountNumber}
              </p>
              <p>
                <span className="font-semibold">Destino:</span> {latestTransfer.receiverAccountNumber}
              </p>
              <p>
                <span className="font-semibold">Monto:</span> {formatCurrency(latestTransfer.amount)}
              </p>
              <p>
                <span className="font-semibold">Fecha:</span> {formatDateTime(latestTransfer.timestamp)}
              </p>
            </div>
          ) : (
            <p className="rounded-xl border border-dashed border-slate-300 p-4 text-sm text-slate-500">
              Aun no has realizado transferencias en esta sesion.
            </p>
          )}
        </Card>
      </div>
    </section>
  )
}
