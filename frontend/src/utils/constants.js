import { ArrowRightLeft, Building2, Landmark } from 'lucide-react'

export const NAV_ITEMS = [
  {
    label: 'Clientes',
    path: '/customers',
    icon: Building2,
  },
  {
    label: 'Transferencias',
    path: '/transfer',
    icon: Landmark,
  },
  {
    label: 'Historico',
    path: '/transactions',
    icon: ArrowRightLeft,
  },
]
