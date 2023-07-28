import { ReactNode } from 'react';
import { createPortal } from 'react-dom';

import PortalLayout from '@components/layout/PortalLayout';

import { styled } from 'styled-components';

interface AlertProps {
  isOpen?: boolean;
  // action: string;
  children?: ReactNode;
}

interface AlertProtoProps {
  children: ReactNode;
}

const Alert = ({ isOpen, children }: AlertProps) => {
  if (!isOpen) return null;

  return createPortal(
    <PortalLayout.Alert>
      <MyAlert>{children}</MyAlert>
    </PortalLayout.Alert>,
    document.body,
  );
};

Alert.Title = ({ children }: AlertProtoProps) => {
  return <MyAlertTitle>{children}</MyAlertTitle>;
};

Alert.Button = ({ children }: AlertProtoProps) => {
  return <MyAlertButton>{children}</MyAlertButton>;
};

const MyAlert = styled.div`
  border-radius: 11px;
  width: 70%;
  font-weight: 600;
`;

const MyAlertTitle = styled.div`
  height: 60px;
  line-height: 60px;
  border-radius: 11px 11px 0 0;
  border-bottom: 1px solid ${({ theme }) => theme.colors.neutral.border};
`;

const MyAlertButton = styled.div`
  height: 50px;
  display: flex;
  border-radius: 0 0 11px 11px;
  justify-content: space-between;
  > button {
    flex-grow: 1;
    &:not(:last-child) {
      border-right: 1px solid ${({ theme }) => theme.colors.neutral.border};
    }
    &:first-child {
      border-radius: 0 0 0 11px;
    }
    &:last-child {
      border-radius: 0 0 11px 0;
    }
    &:hover {
      background-color: ${({ theme }) => theme.colors.neutral.borderStrong};
      color: ${({ theme }) => theme.colors.accent.text};
    }
  }
`;

export default Alert;
