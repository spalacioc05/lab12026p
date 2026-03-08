import { cn } from '../../utils/cn'

export function Input({ label, error, className, ...props }) {
  return (
    <label className="flex w-full flex-col gap-1.5">
      {label ? <span className="text-sm font-semibold text-slate-700">{label}</span> : null}
      <input
        className={cn(
          'w-full rounded-xl border border-slate-300 bg-white px-3 py-2.5 text-slate-800 outline-none transition-all duration-200 placeholder:text-slate-400 focus:border-brand-400 focus:ring-4 focus:ring-brand-100',
          error ? 'border-rose-300 focus:border-rose-400 focus:ring-rose-100' : '',
          className,
        )}
        {...props}
      />
      {error ? <span className="text-xs font-medium text-rose-600">{error}</span> : null}
    </label>
  )
}
