import { Card } from '../ui/Card'

export function StatCard({ label, value, icon: Icon, tone = 'brand' }) {
  const toneClass = tone === 'emerald' ? 'bg-emerald-100 text-emerald-700' : 'bg-brand-100 text-brand-700'

  return (
    <Card className="flex items-center justify-between gap-4">
      <div>
        <p className="text-sm font-medium text-slate-500">{label}</p>
        <p className="mt-1 text-2xl font-extrabold text-ink">{value}</p>
      </div>
      {Icon ? (
        <span className={`inline-flex h-12 w-12 items-center justify-center rounded-xl ${toneClass}`}>
          <Icon size={22} />
        </span>
      ) : null}
    </Card>
  )
}
