'use client'

import Link from 'next/link'
import { MetricTypeDropdownProps } from '@/app/interfaces/Props'

export const Dropdown = ({ metricTypes }: MetricTypeDropdownProps) => (
  <div className='dropdown'>
    <button
      className='btn btn-secondary dropdown-toggle'
      type='button'
      id='dropdownMenuButton'
      data-bs-toggle='dropdown'
      aria-expanded='false'
    >
      Select Metric Type
    </button>
    <ul className='dropdown-menu w-100' aria-labelledby='dropdownMenuButton'>
      {metricTypes.length > 0 ? (
        metricTypes.map((type) => (
          <li key={type.id}>
            <Link href={`/types/edit/${type.id}`} className='dropdown-item'>
              {type.name}
            </Link>
          </li>
        ))
      ) : (
        <li>No types available</li>
      )}
    </ul>
  </div>
)
