import { Landmark } from 'lucide-react'
import { NavLink } from 'react-router-dom'
import { NAV_ITEMS } from '../../utils/constants'
import { cn } from '../../utils/cn'

export function Navbar() {
  return (
    <header className="sticky top-0 z-50 border-b border-slate-200/60 bg-white/90 backdrop-blur-lg">
      <div className="mx-auto flex w-full max-w-7xl items-center justify-between px-4 py-3 md:px-8">
        <div className="flex items-center gap-3">
          <span className="flex h-10 w-10 items-center justify-center rounded-xl bg-brand-600 text-white shadow-glow">
            <Landmark size={20} />
          </span>
          <div>
            <p className="font-display text-lg font-bold text-ink">Banco 2026</p>
            <p className="text-xs text-slate-500">Laboratorio Arquitectura de Software</p>
          </div>
        </div>

        <nav className="flex items-center gap-1 rounded-xl border border-slate-200 bg-white p-1 shadow-soft">
          {NAV_ITEMS.map((item) => {
            const Icon = item.icon
            return (
              <NavLink
                key={item.path}
                to={item.path}
                className={({ isActive }) =>
                  cn(
                    'inline-flex items-center gap-2 rounded-lg px-3 py-2 text-sm font-semibold transition-all',
                    isActive ? 'bg-brand-600 text-white' : 'text-slate-600 hover:bg-slate-100',
                  )
                }
              >
                <Icon size={16} />
                <span className="hidden md:inline">{item.label}</span>
              </NavLink>
            )
          })}
        </nav>
      </div>
    </header>
  )
}
